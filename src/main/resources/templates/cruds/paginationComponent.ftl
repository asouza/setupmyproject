<%@attribute name="paginatedList" required="true" type="java.lang.Object"%>
<%@attribute name="page" required="true"%>
<%@attribute name="action" required="true"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script>
window.onload = function() {		
	$('.pagination').jqPagination({
		link_string	: '<c:url value='${jspExpression.write("action")}'/>?page={page_number}',		
		paged		: function(page) {
			document.location.href = "<c:url value='${jspExpression.write("action")}'/>?page="+(page-1);
		}
	});
};
</script>
<div id="wrapper">
	<div class="gigantic pagination">
	    <a href="#" class="first" data-action="first">&laquo;</a>
	    <a href="#" class="previous" data-action="previous">&lsaquo;</a>
	    <input type="text" readonly="readonly" data-max-page="${jspExpression.write("paginatedList.getNumberOfPages(10)")}" data-current-page="${jspExpression.write("page == null or empty page or page == 0 ? 1 : page+1")}"/>
	    <a href="#" class="next" data-action="next">&rsaquo;</a>
	    <a href="#" class="last" data-action="last">&raquo;</a>
	</div>
</div>