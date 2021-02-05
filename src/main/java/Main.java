import storm.Bolts.*;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;

import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) throws Exception {

        GenerateData generateData = new GenerateData();
        generateData.generate();

        TopologyBuilder builder = new TopologyBuilder();
        InputSpout inputSpout = new InputSpout();
        TimeExtractionBolt timeExtractionBolt = new TimeExtractionBolt();
        TradingBolt tradingBolt = new TradingBolt();
        OutputBolt outputBolt = new OutputBolt();

        builder.setSpout("records", inputSpout,1);
        builder.setBolt("time", timeExtractionBolt,240).fieldsGrouping("records", new Fields("time"));
        builder.setBolt("trade", tradingBolt,40).fieldsGrouping("time", new Fields("tradingSymbol"));
        builder.setBolt("output", outputBolt,1).shuffleGrouping("trade");

        Config config = new Config();
        config.setDebug(true);

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology("mainTopology", config, builder.createTopology());
        TimeUnit.SECONDS.wait(40);
        cluster.killTopology("mainTopology");
        cluster.shutdown();
    }
}
