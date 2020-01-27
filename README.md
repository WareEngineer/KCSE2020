# KCSE2020

## Overview
![overview](https://user-images.githubusercontent.com/24680469/73183064-8b1e3c80-415d-11ea-8da6-c2ce2a13e562.png)
   
## Source Code Structure & Arrangement Rules
![structure and arrangement rules](https://user-images.githubusercontent.com/24680469/73183167-c0c32580-415d-11ea-8037-7ef454e8334a.png)
   
## Algorithm : Source Code Arrangement
* __Input__ : Root node of CodeTree that was composed with CodeLine, CodeBlock  
* __Output__ : String of Sorted Source Code  

```
ARRANGE(TreeNode node) : String  
01 for each subNode of node do
02    ARRANGE(subNode)  
03 end for  
04	  
05 String returnStatement  
06 String buffer  
07 List<String> list  
08	  
09 if node is instance of CodeLine then  
10 |  For each subNode of node do
11 |  |  buffer append node’s string  
12 |  end for  
13 else if node is instance of CodeBlock then  
14 |  while all subNode is not visited do  
15 |  |  list ← new list  
16 |  |  queue ← new queue  
17 |  |  queue push subNode  
18 |  |  while(queue is not empty)  
19 |  |  |  codeLine ← queue pop  
20 |  |  |  
21 |  |  |  if codeLine was visited then   
22 |  |  |  |  continue  
23 |  |  |  else if codeLine is return statement then  
24 |  |  |  |  returnStatement ← codeLine’s string  
25 |  |  |  |  continue  
26 |  |  |  end if   
27 |  |  |  
28 |  |  |  list add codeLine  
29 |  |  |  vars ← get used variablse in codeLine  
30 |  |  |  for each var in vars do
31 |  |  |  |  line ← find a previous codeLine that assigned or defined the var in a block  
32 |  |  |  |  queue push line  
33 |  |  |  end for  
34 |  |  |  
35 |  |  |  vars ← get assigned or defined variables in codeLine  
36 |  |  |  for each var in vars do
37 |  |  |  |  if var is assigned variable then   
38 |  |  |  |  |  line ← find a codeLine that defined the var in a block  
39 |  |  |  |  |  queue push line  
40 |  |  |  |  end if  
41 |  |  |  |  while next codeLine is assignment or definition do   
42 |  |  |  |  |  if the var was used in next codeLine  then  
43 |  |  |  |  |     queue push var  
44 |  |  |  |  |  end if  
45 |  |  |  |  end while  
46 |  |  |  end for  
47 |  |  end while  
48 |  |  sort the list  
49 |  |  buffer append list  
50 |  |  if returnStatement is not null then  
51 |  |     buffer append returnStatement  
52 |  |  end if  
53 |  end while  
54 end if  
55	  
56 return buffer  
```
   
## Reference
* __Data Set__ : ApoGames( https://bitbucket.org/Jacob_Krueger/apogamessrc )
* __API__ : java-diff-utils( https://github.com/java-diff-utils/java-diff-utils )
