import org.apache.storm.spout.ISpout;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class InputSpout extends BaseRichSpout implements ISpout {
    SpoutOutputCollector collector;
    List<Record> recordsList = new ArrayList<>();
    int recordsCounter;

    public void open(Map<String, Object> map, TopologyContext topologyContext, SpoutOutputCollector spoutOutputCollector) {
        String line, splitBy = ",";
        recordsCounter = -1;
        try {
            BufferedReader br = new BufferedReader(new FileReader("records.csv"));
            while ((line = br.readLine()) != null) {
                String[] row = line.split(splitBy);
                Record record = new Record(row[0], row[1], row[2], row[3], row[4], row[5]);
                recordsList.add(record);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        collector = spoutOutputCollector;
    }

    public void nextTuple() {
        recordsCounter++;
        if(recordsCounter<recordsList.size())
            collector.emit(new Values(String.valueOf(recordsList.get(recordsCounter).getTime()),
                    String.valueOf(recordsList.get(recordsCounter).getexchangeCode()),
                    String.valueOf(recordsList.get(recordsCounter).getbuyOrSell()),
                    String.valueOf(recordsList.get(recordsCounter).getTradingSymbol()),
                    String.valueOf(recordsList.get(recordsCounter).getCost()),
                    String.valueOf(recordsList.get(recordsCounter).getEnteredShareNumber())));
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("time", "exchangeCode", "buyOrSell", "tradingSymbol", "cost", "enteredShareNumber"));
    }
}
