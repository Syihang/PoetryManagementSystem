# PoetryManagementSystem
诗词管理系统，Java大作业，采用JavaSwing实现GUI界面，数据库选择了Mysql数据库 (具体内容见项目设计书)



#### 功能模块图

<img src=".\image\image-20240801113145720.png" alt="image-20240801113145720" />

<img src=".\image\image-20240801113215716.png" alt="image-20240801113215716" />

#### 数据库设计

本系统所需的表如下：

（1）用户表（user）该表结构如表1.1所示。

表1.1 用户表



| 字段          | 类型     | 是否为空 | 主键        | 中文描述 |
| ------------- | -------- | -------- | ----------- | -------- |
| user_id       | INT      | NOT NULL | PRIMARY KEY | 用户ID   |
| user_account  | CHAR(20) | NOT NULL | NO          | 用户名   |
| user_password | CHAR(20) | NOT NULL | NO          | 密码     |
| Telephone     | CHAR(20) | NULL     | NO          | 联系电话 |



 

（2）管理员表（admin）该表结构如表1.2所示。

表1.2管理员表

| 字段           | 类型     | 是否为空  | 主键         | 中文描述   |
| -------------- | -------- | --------- | ------------ | ---------- |
| admin_id       | INT      | NOT  NULL | PRIMARY  KEY | 管理员ID   |
| admin_account  | CHAR(20) | NOT  NULL | NO           | 管理员账号 |
| admin_password | CHAR(20) | NOT  NULL | NO           | 密码       |
| admin_name     | CHAR(20) | NULL      | NO           | 姓名       |
| Telephone      | CHAR(20) | NULL      | NO           | 电话       |

 

（3）诗词表（poetry）该表结构如表1.3所示。

表1.3诗词表

| 字段      | 类型        | 是否为空  | 主键         | 中文描述 |
| --------- | ----------- | --------- | ------------ | -------- |
| poetry_id | INT         | NOT  NULL | PRIMARY  KEY | 诗词编号 |
| title     | VARCHAR(20) | NULL      | NO           | 标题     |
| author    | VARCHAR(20) | NULL      | NO           | 作者     |
| dynasty   | CHAR(10)    | NULL      | NO           | 作者朝代 |
| type      | CHAR(10)    | NULL      | NO           | 类别     |
| text      | TEXT        | NOT NULL  | NO           | 诗词正文 |

（4）诗词表（poetry）该表结构如表1.3所示。

表1.4收藏表

| 字段      | 类型 | 是否为空  | 主键         | 中文描述 |
| --------- | ---- | --------- | ------------ | -------- |
| user_id   | INT  | NOT  NULL | PRIMARY  KEY | 用户ID   |
| Poetry_ID | INT  | NOT NULL  | PRIMARY KEY  | 诗词编号 |

其中，在表1.1  1.2 1.3 中均设置了主键自增，表1.4的两个主键分别为表1.1与表1.2的外键。

#### 界面效果图

<img src=".\image\image-20240801113325382.png" alt="image-20240801113325382" />

<img src=".\image\image-20240801113412443.png" alt="image-20240801113412443" />

<img src=".\image\image-20240801113441179.png" alt="image-20240801113441179" />

<img src=".\image\image-20240801113512066.png" alt="image-20240801113512066" />

<img src=".\image\image-20240801113538399.png" alt="image-20240801113538399" />

<img src=".\image\image-20240801113607934.png" alt="image-20240801113607934" />

<img src=".\image\image-20240801113637657.png" alt="image-20240801113637657" />

<img src=".\image\image-20240801113717073.png" alt="image-20240801113717073" />
