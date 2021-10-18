import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;


/**
 * 
 */
public class BestTimeToBuyAndSellStockWithFee {


    // **** memoization ****
    static private HashMap<String, Integer> memo = null;


    /**
     * Find the maximum profit you can achieve.
     * Brute force approach.
     * Recursion entry point.
     * 
     * 44 / 44 test cases passed.
     * Status: Accepted
     * Runtime: 682 ms
     * Memory Usage: 475.6 MB
     */
    static public int maxProfitBF(int[] prices, int fee) {

        // **** initialize memoization ****
        memo = new HashMap<>();

        // **** start recursion and return result ****
        return dpBF(prices, fee, 0, false);
    }


    /**
     * Recursive dynamic programming function.
     * Started with brute force approach and ended
     * with DP with memoization solution.
     */   
    static private int dpBF(int[] prices, int fee, int day, Boolean bought) {

        // **** base case ****
        if (day >= prices.length) return 0;

        // **** initialization ****
        int maxProfit = 0;

        // **** build the key ****
        String key = "" + day + " " + (bought ? "1" : "0");

        // **** check memo ****
        if (memo.containsKey(key)) return memo.get(key);

        // **** buy stock (if needed) ****
        if (!bought)
            maxProfit = Math.max(maxProfit, dpBF(prices, fee, day + 1, true) - prices[day] - fee);

        // **** sell stock (if needed) ****
        else
            maxProfit = Math.max(maxProfit, dpBF(prices, fee, day + 1, false) + prices[day]);

        // **** do nothing ****
        maxProfit = Math.max(maxProfit, dpBF(prices, fee, day + 1, bought));


        // **** update memo ****
        // if (maxProfit != 0) memo.put(key, maxProfit);
        memo.put(key, maxProfit);

        
        // **** return the max profit ****
        return maxProfit;
    }


    /**
     * Find the maximum profit you can achieve.
     * Iterative approach.
     * 
     * 44 / 44 test cases passed.
     * Status: Accepted
     * Runtime: 3 ms
     * Memory Usage: 48.2 MB
     * 
     * Runtime: O(n) - Space O(1)
     */
    static public int maxProfit(int[] prices, int fee) {

        // **** initialization ****
        int hold    = -prices[0];       // buy stock
        int cash    = 0;                // do not buy stock 

        // **** loop for each day - O(n) ****
        for (int day = 1; day < prices.length; day++) {

            // **** sell stock ****
            cash = Math.max(cash, hold + prices[day] - fee);

            // **** hold stock ****
            hold = Math.max(hold, cash - prices[day]);

            // ???? ????
            System.out.println( "maxProfit <<< day: " + day + " price: " + prices[day] + 
                                " cash: " + cash + " hold: " + hold);
        }

        // **** return cash at hand (profit) ****
        return cash;
    }


    /**
     * Find the maximum profit you can achieve.
     * Recursion entry point.
     * Does not make use of memoization!
     * 
     * 19 / 44 test cases passed.
     * Status: Time Limit Exceeded
     */
    static public int maxProfitNoMemo(int[] prices, int fee) {
        return dp(prices, fee, 0, false);
    }


    /**
     * Recursive dynamic programming function.
     * No memoization!
     */
    static private int dp(int[] prices, int fee, int day, boolean bought) {

        // **** base case ****
        if (day >= prices.length) return 0;

        // **** initialization ****
        int maxProfit = 0;
        
        // **** buy stock state ****
        if (!bought)
            maxProfit = Math.max(maxProfit, dp(prices, fee, day + 1, true) - prices[day] - fee);    

        // **** sell stock state ****
        else
            maxProfit = Math.max(maxProfit, dp(prices, fee, day + 1, false) + prices[day]);
        
        // **** do nothing state ****
        maxProfit = Math.max(maxProfit, dp(prices, fee, day + 1, bought));
        
        // **** return the max profit ****
        return maxProfit;
    }


    /**
     * Test scaffold
     * NOT PART OF SOLUTION
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        
        // **** open buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read prices int[] array ****
        int[] prices = Arrays.stream(br.readLine().trim().split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();

        // **** read int fee ****
        int fee = Integer.parseInt(br.readLine().trim());

        // *** close buffered reader ****
        br.close();

        // ???? ????
        System.out.println("main <<< prices: " + Arrays.toString(prices));
        System.out.println("main <<< fee: " + fee);

        // **** call function of interest and display result ****
        System.out.println("main <<< Output: " + maxProfitNoMemo(prices, fee));

        // **** call function of interest and display result ****
        System.out.println("main <<< Output: " + maxProfitBF(prices, fee));

        // **** call function of interest and display result ****
        System.out.println("main <<< Output: " + maxProfit(prices, fee));
    }
}