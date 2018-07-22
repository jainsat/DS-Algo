import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

/*
 *  Problem - https://www.hackerrank.com/contests/booking-womenintech/challenges/morph-search-results/problem
 *  LCS can be calculated using LIS in nlogn time if elements both of subsequences are unique and both subsequences
 *  share same elements but differ in order.
 *  https://stackoverflow.com/questions/30768610/finding-longest-common-subsequence-in-onlogn-time
 */
public class Solution {

    // Complete the solve function below.
    static int solve(List<Integer> old, List<Integer> newl) {
        Set<Integer> so = new HashSet<>(old);
        Set<Integer> sn= new HashSet<>(newl);
        List<Integer> lo = new ArrayList<>();
        List<Integer> ln = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();

        int ans=0;
        // Remove
        for (Integer i : old) {
            if (!sn.contains(i)) {
                ans++;
            }
            else {
                lo.add(i);
                map.put(i, lo.size()-1);
            }
        }
        // Add
        for (Integer i : newl) {
            if (!so.contains(i)) {
                ans++;
            }
            else {
                ln.add(i);
            }
        }
        int arr[]= new int[lo.size()];int j=0;
        for (Integer i : ln) {
            arr[j++]=map.get(i);
        }
        return ans + lo.size() - lis(arr);
    }
    static int lis(int arr[])
    {
        int dp[]=new int[arr.length];
        int e = 1;
        dp[0]=arr[0];
        for (int i=1;i<arr.length;i++) {
            if (arr[i] < dp[0]) {
                dp[0]=arr[i];
            }
            else if (arr[i]>dp[e-1]) {
                dp[e++]=arr[i];
            }
            else {
                dp[ceil(dp,-1,e-1,arr[i])]=arr[i];
            }
        }
        return e;
    }
    static int ceil(int arr[], int l, int r, int k)
    {
        if (l > r) {
            return -1;
        }
        int mid=(l+r)/2;
        if (arr[mid] < k) {
            return ceil(arr,mid+1,r,k);
        }
        else if(arr[mid]==k) {
            return mid;
        }
        else {
            if ((mid-1)>= l && arr[mid-1] >= k) {
                return ceil(arr,l,mid-1,k);
            }
            return mid;
        }
    }


    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        String[] nm = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        int n = Integer.parseInt(nm[0]);

        int m = Integer.parseInt(nm[1]);

        String[] list1Items = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> list1 = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int list1Item = Integer.parseInt(list1Items[i]);
            list1.add(list1Item);
        }

        String[] list2Items = bufferedReader.readLine().replaceAll("\\s+$", "").split(" ");

        List<Integer> list2 = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            int list2Item = Integer.parseInt(list2Items[i]);
            list2.add(list2Item);
        }

        int result = solve(list1, list2);

        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        bufferedReader.close();
        bufferedWriter.close();
    }
}