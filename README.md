## Exchange Information Analysis using Stream Processing

In this project, we are going to analyze the exchange data using Apache Storm for Stream Processing. As we have no real exchange data we use random data for analysis. 
In this project, we have some trading symbols. Assume that the trading symbols are from 1 to 40. The cost of each share is equal to its trading symbol multiply by 100 for example cost of the trading symbol with id 37 is equal to 3700. It can fluctuate to 1%. So a trading symbol with id 37 can be negotiated from 3663 Rial to 3737. Assume that we have a 10000 exchange code between 1 to 10000.
<br />
<br />
Exchange activity in Iran starts at 8:30 and ends at 12:30. I have written a program to generate exchange data randomly. Each trading symbol has to have a sell or buy order in a minute.

Sell or buy records should be as below:
Time, Exchange Code, Sell or Buy (Sell 0 Buy 1), Trading Symbol ID, Cost, Shared Number

For example, a record can be like:<br />
**08:32:28, 162, 1, 29, 2910, 97**

So Exchange code with ID 162 at 08:32:28 has ordered 97 shares from ID 29 with a cost of  2910 Rial.

**Assume that Shared numbers are between 1 and 100.**
  
As each Exchange code has to have at least one sell or buy order in a minute and the number of all exchange codes are 10000 and activities can be done between 4 hours or 240 minutes, so we should generate 24,000,000 random records.

Random records will be stored in a .csv file and they will be processed with Apache Storm. <br />
At the end of the process, the output will be stored in a .csv file containing the number of each trading symbol's successful negotiation:

1,253<br />
2,0<br />
...<br />
40,10
