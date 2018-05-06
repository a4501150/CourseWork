<%@include file="../partials/header.jsp" %>
<%@ page import = "team6.entity.RoomType" %>
<%@ page import = "java.util.List" %>

<%-- TODO: frontend --%>
<h2>Add a hotel</h2>
 <form method="post" enctype="multipart/form-data" action="<%= rootPath %>/hotel/add">
   <table>
       <tr>
           <th><b>Hotel name:</b></th>
           <td><input required name="name" type="text" size="40"></td>
       </tr>
       <tr>
           <th><b>Address: </b></th>
           <td><input required name="address" type="text" size="10"></td>
       </tr>
       <tr>
           <th><b>City: </b></th>
           <td><input name="city" type="text" size="10"></td>
       </tr>
       <tr>
           <th><b>State: </b></th>
           <td><input name="state" type="text" size="2"></td>
       </tr>
       <tr>
           <th><b>ZIP Code: </b></th>
           <td><input required name="zip" type="text" size="5"></td>
       </tr>
       <tr>
           <th><b>Description </b></th>
           <td><textarea required name="description"></textarea></td>
       </tr>
       <tr>
           <th><b>Images: </b></th>
           <td><input required name="image[]" type="file" multiple></td>
       </tr>
   </table>
   
   <button type="submit">Submit</button>   
 </form>

<%@include file="../partials/footer.jsp" %>