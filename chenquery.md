<!-- 办公用品领用申请sql和hql -->


&lt;sql-query name="oa\_searchOaProductAppList"&gt;


<![CDATA[
> select app.**,s\_users.login\_name
> from oa\_product\_app  app ,s\_users
> where s\_users.user\_id=app.create\_by
> and app.create\_by=?
> and status=?
  1. and app.create\_date>=?]
  1. and app.create\_date<=?]**

> ]]>


&lt;/sql-query&gt;



采用#[.md](.md)作为判断机制，sql简洁实用，简化开发过程！