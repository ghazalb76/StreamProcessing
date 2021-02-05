package storm.Bolts;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

public class TimeExtractionBolt extends BaseRichBolt{
    OutputCollector collector;

    public void prepare(Map<String, Object> map, TopologyContext topologyContext, OutputCollector outputCollector) {
        collector = outputCollector;
    }

    public void execute(Tuple tuple) {
        collector.emit(tuple, new Values(tuple.getString(1), tuple.getString(2), tuple.getString(3), tuple.getString(4), tuple.getString(5)));
        collector.ack(tuple);
    }

    public void cleanup() {}

    public void declareOutputFields(OutputFieldsDeclarer outputFieldsDeclarer) {
        outputFieldsDeclarer.declare(new Fields("exchangeCode", "buyOrSell", "tradingSymbol", "cost", "enteredShareNumber"));
    }
}