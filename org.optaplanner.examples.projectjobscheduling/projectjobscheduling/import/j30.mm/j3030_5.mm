************************************************************************
file with basedata            : mf30_.bas
initial value random generator: 1109360330
************************************************************************
projects                      :  1
jobs (incl. supersource/sink ):  32
horizon                       :  239
RESOURCES
  - renewable                 :  2   R
  - nonrenewable              :  2   N
  - doubly constrained        :  0   D
************************************************************************
PROJECT INFORMATION:
pronr.  #jobs rel.date duedate tardcost  MPM-Time
    1     30      0       30       17       30
************************************************************************
PRECEDENCE RELATIONS:
jobnr.    #modes  #successors   successors
   1        1          3           2   3   4
   2        3          3          12  15  16
   3        3          3           9  14  22
   4        3          3           5   6  10
   5        3          3           7  24  26
   6        3          3           7  11  24
   7        3          3           8  19  29
   8        3          2          17  20
   9        3          3          12  13  21
  10        3          2          21  23
  11        3          1          14
  12        3          3          18  26  30
  13        3          2          15  19
  14        3          1          25
  15        3          2          23  28
  16        3          2          22  29
  17        3          3          21  23  30
  18        3          1          24
  19        3          2          28  30
  20        3          1          22
  21        3          1          25
  22        3          1          25
  23        3          1          27
  24        3          1          27
  25        3          2          27  28
  26        3          1          29
  27        3          1          31
  28        3          1          31
  29        3          1          32
  30        3          1          32
  31        3          1          32
  32        1          0        
************************************************************************
REQUESTS/DURATIONS:
jobnr. mode duration  R 1  R 2  N 1  N 2
------------------------------------------------------------------------
  1      1     0       0    0    0    0
  2      1     4       6    5   10    0
         2     5       5    3    9    0
         3    10       4    2    0    8
  3      1     6       2   10    5    0
         2    10       1    7    5    0
         3    10       2    4    0    5
  4      1     2       2    7    0    9
         2     3       1    7    3    0
         3     4       1    5    0    8
  5      1     4       5    6    0    2
         2     7       4    6    0    1
         3     9       3    3    5    0
  6      1     6       2    7    3    0
         2     9       2    7    0    3
         3     9       2    6    0    7
  7      1     3       5    8    0    1
         2     8       4    8    6    0
         3     9       3    6    6    0
  8      1     6       8    7    0    9
         2     7       6    4    3    0
         3     9       4    2    2    0
  9      1     4       3    9    0    4
         2     5       2    7    0    3
         3     5       2    8    7    0
 10      1     4       7    4    7    0
         2     7       6    4    2    0
         3     8       4    3    0    7
 11      1     1       5    6    0    2
         2     1       5    7   10    0
         3     8       3    3    9    0
 12      1     2       9    5    0   10
         2     8       6    5    9    0
         3    10       1    5    5    0
 13      1     1       7    6    0    8
         2     3       5    5    4    0
         3     5       4    2    0    7
 14      1     3       9    6    0    7
         2     6       8    6    0    4
         3     9       5    5    8    0
 15      1     1       7    5    0    6
         2     1       7    6    7    0
         3     4       4    5    6    0
 16      1     3       7    9    6    0
         2     9       5    6    6    0
         3    10       2    4    5    0
 17      1     1       8    5    0    8
         2     3       5    4    0    8
         3     5       4    3    5    0
 18      1     1       3    6   10    0
         2     5       2    5    8    0
         3    10       2    5    7    0
 19      1     2       5    6    5    0
         2     7       4    5    3    0
         3     7       4    5    0    8
 20      1     2       5    7    9    0
         2     4       3    5    0    8
         3     5       2    4    0    7
 21      1     2       7    8    0    6
         2     7       6    8    5    0
         3     9       3    7    0    5
 22      1     2       6    2    8    0
         2     5       4    1    6    0
         3    10       4    1    0    2
 23      1     4       8    3    0    2
         2     7       3    2    0    1
         3     7       2    3    0    1
 24      1     7       6    7    0    3
         2     7       7    6    0    2
         3     7       8    7    5    0
 25      1     4       9    5    0    4
         2     7       9    2    4    0
         3     9       9    2    3    0
 26      1     5       4    5    6    0
         2     6       3    3    3    0
         3     6       3    4    0    9
 27      1     2       9    9    0    9
         2     4       8    7    7    0
         3     9       8    1    5    0
 28      1     2       6    3    0   10
         2     3       5    2    0    6
         3    10       4    2    8    0
 29      1     5       4    9   10    0
         2     6       4    4    0    2
         3     8       4    4   10    0
 30      1     3       9    4    4    0
         2     3       8    4    0    5
         3     9       7    3    4    0
 31      1     3       7    6    0    9
         2     8       7    4    0    8
         3     9       4    3    0    8
 32      1     0       0    0    0    0
************************************************************************
RESOURCEAVAILABILITIES:
  R 1  R 2  N 1  N 2
   26   23  179  170
************************************************************************
