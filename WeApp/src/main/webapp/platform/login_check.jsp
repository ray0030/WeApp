<%
//下面是比较,这里我们不用数据库，root和123456都是我在这里假设的用户名和密码
int i=0;
if(i==0)
{
%>
<jsp:forward page="home.jsp"/> //验证完毕后要前往的页面

<%
}
else
{
out.print("用户名或密码错误");
}
%>