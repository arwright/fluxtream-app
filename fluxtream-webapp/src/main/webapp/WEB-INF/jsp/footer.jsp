<footer id="footer">
<% int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR); String until = ""; if (currentYear>2011) until = " - " + currentYear; %>
<% String release = ""; if (request.getAttribute("release")!=null) release = "Release " + request.getAttribute("release") + "";%>
	<div class="floatL bMargin">
		<small>Fluxtream - <%=release %> &copy; Candide Kemmler 2011<%=until %></small>
	</div>
</footer>