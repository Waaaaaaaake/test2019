#include <iostream>
#include "math.h"
using namespace std;

int countOfTickets(int n) {
  int result = 0;
  int arr[n];
  int g = 0, p = (n-1), sum1 = 0, sum2 = 0, j = 0;

  for (int i = 0; i < pow(10,n); i++) {
      for (int k = i, j = 0; j < n; j++, k /= 10) {
          arr[j] = k % 10;
      }
      while (g < p) {
        sum1+=arr[g++];
        sum2+=arr[p--];
      }

      if (sum1 == sum2) {
        result++;
      }
      g = 0;
      p = n-1;
      sum1 = 0;
      sum2 = 0;
  }
  return result;
}

int main() {
    int n;
    cin>>n;
    cout<<countOfTickets(n);
    return 0;
}
