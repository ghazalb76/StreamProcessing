import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateData {
    int hour = 8;
    int minute = 30;
    int exchangeCode = 1;
    public void generate(){
        Random random = new Random();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("records.csv"))) {
            String time = generateTime();
            for (int i=0;i<2400000;i++) {

                int tradingSymbol = random.nextInt(40)+1;
                int minCost = (tradingSymbol*100) - tradingSymbol;
                int maxCost = (tradingSymbol*100) + tradingSymbol;

                writer.append(time);
                writer.append(",");
                writer.append(String.valueOf(exchangeCode));
                writer.append(",");
                writer.append(String.valueOf(random.nextInt(2)));
                writer.append(",");
                writer.append(String.valueOf(tradingSymbol));
                writer.append(",");
                writer.append(String.valueOf(random.nextInt(maxCost-minCost) + minCost));
                writer.append(",");
                writer.append(String.valueOf(random.nextInt(100)+1));
                writer.newLine();
                exchangeCode++;
                if(exchangeCode > 10000){
                    exchangeCode = 1;
                    time = generateTime();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String generateTime(){
        if(minute == 60){
            minute = 0;
            hour ++;
        }
        StringBuilder hourString = new StringBuilder();
        if(hour == 8 || hour == 9){
            hourString.append("0");
            hourString.append(hour);
        }
        else {
            hourString.append(hour);
        }
        StringBuilder minuteString = new StringBuilder();
        if(minute < 10){
            minuteString.append("0");
            minuteString.append(minute);
        }
        else {
            minuteString.append(minute);
        }

        StringBuilder time = new StringBuilder();
        time.append(hourString);
        time.append(":");
        time.append(minuteString);
        time.append(":");
        time.append("00");
        minute++;
        return String.valueOf(time);
    }
}
