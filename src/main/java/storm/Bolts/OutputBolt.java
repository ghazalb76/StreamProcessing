package storm.Bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Tuple;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class OutputBolt extends BaseRichBolt {
    Map<String, String> resultHashMap = new HashMap();
    OutputCollector collector;
    int counter = 0;

    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        counter++;
//        System.out.println(counter + "  OUTPUT: [" + tuple.getString(0) + ", " + tuple.getString(1) + "]");
        if (resultHashMap.containsKey(tuple.getString(0))) {
            resultHashMap.replace(tuple.getString(0), tuple.getString(1));
        } else {
            resultHashMap.put(tuple.getString(0), tuple.getString(1));
        }
        if (counter == 2400000) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("result.csv"))) {
                for (int i = 1; i <= 40; i++) {
                    writer.append(String.valueOf(i));
                    writer.append(",");
                    writer.append(resultHashMap.get(String.valueOf(i)));
                    writer.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        collector.ack(tuple);
    }

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
    }

}
