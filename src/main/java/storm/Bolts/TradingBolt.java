package storm.Bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TradingBolt extends BaseRichBolt{
    OutputCollector collector;
    HashMap<Integer, Integer> buyHashMap = new HashMap<>();
    HashMap<Integer, Integer> sellHashMap = new HashMap<>();

    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        int buyOrSell = Integer.parseInt(tuple.getString(1));
        int tradingSymbol = Integer.parseInt(tuple.getString(2));
        int cost = Integer.parseInt(tuple.getString(3));
        int enteredShareNumber = Integer.parseInt(tuple.getString(4));
        if(buyOrSell == 0) {
            if (buyHashMap.containsKey(cost)) {
                buyHashMap.replace(cost, buyHashMap.get(cost) + enteredShareNumber);
            } else {
                buyHashMap.put(cost, enteredShareNumber);
            }
        } else if (buyOrSell == 1){
            if (sellHashMap.containsKey(cost)) {
                sellHashMap.replace(cost, sellHashMap.get(cost) + enteredShareNumber);
            } else {
                sellHashMap.put(cost, enteredShareNumber);
            }
        }
        int tradingShareNumber = 0;
        if(buyHashMap.size()!=0 && sellHashMap.size()!=0) {
            Iterator iterator = buyHashMap.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry pair = (Map.Entry) iterator.next();
                if (sellHashMap.containsKey(pair.getKey())) {
                    if (buyHashMap.get(pair.getKey()) < sellHashMap.get(pair.getKey())) {
                        tradingShareNumber = buyHashMap.get(pair.getKey());
                    } else {
                        tradingShareNumber = sellHashMap.get(pair.getKey());
                    }
                }
            }
        }
        collector.emit(tuple, new Values(String.valueOf(tradingSymbol), String.valueOf(tradingShareNumber)));
        collector.ack(tuple);
    }

    public void cleanup() {}

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("tradingSymbol", "tradingShareNumber"));

    }
}