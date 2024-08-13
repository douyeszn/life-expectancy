import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Statistics {

    private List<Integer> survivalRates;;

    public Statistics(List<Integer> survivalRates) {
        this.survivalRates = survivalRates;
    }
    public double getMean() {
        double sum = 0.0;
        for (int number : this.survivalRates) {
            sum += number;
        }
        return sum / this.survivalRates.size();
    }

    public double getMedian() {
        Collections.sort(this.survivalRates);
        int size = this.survivalRates.size();
        if (size % 2 == 0) {
            return (this.survivalRates.get(size / 2 - 1) + this.survivalRates.get(size / 2)) / 2.0;
        } else {
            return this.survivalRates.get(size / 2);
        }
    }

    public double getPercentile(double percentile) {
        Collections.sort(this.survivalRates);
        int index = (int) Math.ceil(percentile / 100.0 * this.survivalRates.size()) - 1;
        return this.survivalRates.get(Math.max(index, 0));
    }

    public void export() {
        String fileName = "user-survival-stats.txt";
        double mean = this.getMean();
        double median = this.getMedian();
        double percentile25 = this.getPercentile(25);
        double percentile50 = this.getPercentile(50);
        double percentile75 = this.getPercentile(75);

        String meanStr = String.format("Mean: %.2f", mean);
        String medianStr = String.format("Median: %.2f", median);
        String percentile25Str = String.format("25th Percentile: %.2f", percentile25);
        String percentile50Str = String.format("50th Percentile: %.2f", percentile50);
        String percentile75Str = String.format("75th Percentile: %.2f", percentile75);

        System.out.println(meanStr + "," + medianStr + "," + percentile25Str + "," + percentile50Str + "," + percentile75Str);

        try {
            String[] cmd = new String[]{"resource/export.sh", fileName, meanStr, medianStr, percentile25Str, percentile50Str, percentile75Str};
            ProcessBuilder pb = new ProcessBuilder(cmd);
            Process process = pb.start();
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

}
