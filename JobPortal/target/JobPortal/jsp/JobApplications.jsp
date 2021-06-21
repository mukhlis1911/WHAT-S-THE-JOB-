
<%@page import="in.co.job.portal.model.UserModel"%>
<%@page import="in.co.job.portal.util.DataUtility"%>
<%@page import="in.co.job.portal.model.JobApplicationModel"%>
<%@page import="in.co.job.portal.model.JobModel"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="in.co.job.portal.bean.JobApplicationBean"%>
<%@page import="in.co.job.portal.bean.JobBean"%>
<%@page import="in.co.job.portal.controller.JobApplicationsCtl"%>
<%@page import="in.co.job.portal.util.ServletUtility"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Job Applications</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<main class="login-form">
	<div class="cotainer">
		<div class="row justify-content-center">
			<div class="col-md-12">
				<div class="card">
					<div class="card-header">
						Job Applications List

						<h6 style="color: red;"><%=ServletUtility.getErrorMessage(request)%></h6>
						<h6 style="color: green;"><%=ServletUtility.getSuccessMessage(request)%></h6>

					</div>

					<div class="card-body">

						<form action="<%=JPView.JOB_APPLICATION_CTL%>" method="post">



							<table class="table">

								<thead>
									<tr>


										<th><b>S No.</b></th>
										<th><b>Name of Applicant</b></th>
										<th><b>Applied for Job</b></th>
										<th><b>Apply Date</b></th>
										<th><b>Contact No.</b></th>
										<th><b>Action</b></th>

									</tr>
								</thead>

								<%
									int pageNo = ServletUtility.getPageNo(request);
								int pageSize = ServletUtility.getPageSize(request);
								int index = ((pageNo - 1) * pageSize) + 1;

								JobApplicationBean bean = null;

								List list = ServletUtility.getList(request);

								Iterator<JobApplicationBean> it = list.iterator();

								while (it.hasNext()) {
									bean = it.next();
								%>
								<tbody>
									<tr>


										<%
											JobModel jModel = new JobModel();
										JobBean jBean = jModel.findByPK(bean.getJobId());
										UserModel uModel = new UserModel();
										UserBean uBean = uModel.findByPK(bean.getUserId());
										
										
										
										%>


										<td align="center"><%=index++%></td>

										<td><%=bean.getUserName()%></td>

										<td align="center"><%=jBean.getDescription()%></td>


										<td align="center"><%=bean.getApplyDate()%></td>
										<td align="center"><%=uBean.getMobileNo()%></td>


										<td>
										<%  if("Y".equalsIgnoreCase(bean.getProcessed())) { %>
										
										<input
											style="background-color: green; color: white;"
											 class="form-control" type="submit"
											value="Accepted" disabled>
				
										
										<%} else { %>
										<input
											style="background-color: #0b889c; color: white;"
											type="submit" class="form-control" name="<%=bean.getId()%>"
											value="<%=JobApplicationsCtl.OP_ACCEPT%>">
											
											<%} %>
											
										</td>







									</tr>

								</tbody>
								<%
									}
								%>
							</table>

							<table class="table">
								<thead>
									<tr>
										<th><input
											style="background-color: #0b889c; color: white;"
											type="submit" class="form-control" name="operation"
											value="<%=JobApplicationsCtl.OP_PREVIOUS%>"
											<%=(pageNo == 1) ? "disabled" : ""%>></th>

										<%
											JobApplicationModel model = new JobApplicationModel();
										%>
										<th><input
											style="background-color: #0b889c; color: white;"
											type="submit" class="form-control" name="operation"
											value="<%=JobApplicationsCtl.OP_NEXT%>"
											<%=((list.size() < pageSize) || model.nextPK() - 1 == bean.getId()) ? "disabled" : ""%>></th>
									</tr>
								</thead>

							</table>
							<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
								type="hidden" name="pageSize" value="<%=pageSize%>">

						</form>
					</div>

				</div>
			</div>
		</div>
	</div>
	</main>
	<%@ include file="Footer.jsp"%>
</body>
</html>