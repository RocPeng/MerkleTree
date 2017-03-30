客户端： 
1通信模块：利用TCP的socket实现上传下载功能。上传前对文件进行求hash值作为文件检索索引用于去重，并保存本地。
2 Merkle树模块：固定粒度分块文件（比如1byte/分块）。利用SHA-1生成完整的Merkle树，并保存在本地。
3完整性验证模块：发送任意叶子节点请求比如D1，等待接受服务器返回的根哈希值。并比对本地根哈希值进行判断。
4数据去重：先根据hash值索引向云端发送索引请求，根据索引结果判断是否上传/更新文件。

云服务器：
1通信模块：利用TCP的socket实现上传下载功能。保存文件hash值作为文件索引用于去重。
(维护一张hash表:hash:索引(即文件名,可能多个)  如果上传的文件hash相同  文件名不同则保存多个文件名)
2 Merkle树模块：固定粒度分块文件（比如4个二进制位/分块）。利用SHA-1生成完整的Merkle树，并保存在本地。
(客户端和本地用相同算法,保存两份默克尔树文件)
3完整性验证模块：接收叶子节点请求比如D1，用该节点计算出新的根哈希值发送给客户端。
(客户端的叶子节点随机给1个(叶子节点是4位二进制分块后的文件块求得hash))

4数据去重：根据接收客户端的索引请求，若检测到相同索引，则计算上传文件Merkle树与云端文件Merkle树进行对比，删去相同数据块保留不同数据块；若未检测到相同索引，则;反馈给用户，并上传/更新文件。（尽量做数据级去重）。

5.不稳定模块：可选择在保存客户端文件前是否篡改文件，为体现雪崩效应，只随机修改一个二进制位。

