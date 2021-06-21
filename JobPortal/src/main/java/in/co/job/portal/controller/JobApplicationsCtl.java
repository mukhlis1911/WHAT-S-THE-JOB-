package in.co.job.portal.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import in.co.job.portal.bean.BaseBean;
import in.co.job.portal.bean.JobApplicationBean;
import in.co.job.portal.bean.UserBean;
import in.co.job.portal.exception.ApplicationException;
import in.co.job.portal.model.JobApplicationModel;
import in.co.job.portal.model.JobModel;
import in.co.job.portal.model.UserModel;
import in.co.job.portal.util.DataUtility;
import in.co.job.portal.util.EmailBuilder;
import in.co.job.portal.util.EmailMessage;
import in.co.job.portal.util.JobApplicationSendMail;
import in.co.job.portal.util.PropertyReader;
import in.co.job.portal.util.ServletUtility;


@WebServlet(name = "JobApplicationsCtl", urlPatterns = { "/ctl/JobApplicationsCtl" })
public class JobApplicationsCtl extends BaseCtl {
	private static Logger log = Logger.getLogger(JobApplicationsCtl.class);

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		JobApplicationBean bean = new JobApplicationBean();

		// bean.setUserName(DataUtility.getString(request.getParameter("name")));

		return bean;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		

		String op = DataUtility.getString(request.getParameter("operation"));

		List list = null;

		int pageNo = 1;

		int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

		JobApplicationBean bean = (JobApplicationBean) populateBean(request);

		String ids = request.getParameter("ids");

		JobApplicationModel model = new JobApplicationModel();
		try {

			HttpSession session = request.getSession();
			UserBean uBean = (UserBean) session.getAttribute("user");

			bean.setRecruiterId(uBean.getId());

			list = model.jobApplicationSearch(bean, pageNo, pageSize);

			if (list == null || list.size() == 0) {

				ServletUtility.setErrorMessage("No record found ", request);
			}

			ServletUtility.setList(list, request);
			ServletUtility.setPageNo(pageNo, request);
			ServletUtility.setPageSize(pageSize, request);
			ServletUtility.forward(getView(), request, response);
		} catch (ApplicationException e) {
			log.error(e);
			ServletUtility.handleException(e, request, response);
			return;
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		try {
			System.out.println("in post");

			String op = DataUtility.getString(request.getParameter("operation"));

			Enumeration<String> paramNames = request.getParameterNames();

			if (!OP_NEXT.equalsIgnoreCase(op) && !OP_PREVIOUS.equalsIgnoreCase(op)) {
				while (paramNames.hasMoreElements()) {
					String temp = paramNames.nextElement();
					if (!temp.equalsIgnoreCase("pageNo") && !temp.equalsIgnoreCase("pageSize")) {

						int tempid = Integer.parseInt(temp);
						JobModel jobModel = new JobModel();
						JobApplicationModel jobApplicationModel = new JobApplicationModel();

						jobModel.updateJobStatus(tempid);
						jobApplicationModel.updateJobApplicationStatus(tempid);

						JobApplicationBean jobApplicationBean = jobApplicationModel.findByPK(tempid);
						UserModel userModel = new UserModel();

						UserBean userBean = userModel.findByPK(jobApplicationBean.getUserId());

						String userName = jobApplicationBean.getUserName();

						String message = EmailBuilder.getJobApplicationAcceptMsg(userName);

						EmailMessage msg = new EmailMessage();

						msg.setTo(userBean.getLogin());
						msg.setSubject("Your Job Application is accepted");
						msg.setMessage(message);
						msg.setMessageType(EmailMessage.HTML_MSG);
						JobApplicationSendMail.sendMail(msg);

					}
				}
			}

			List list = null;
			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			JobApplicationBean bean = (JobApplicationBean) populateBean(request);

			String[] ids = request.getParameterValues("ids");

			JobApplicationModel model = new JobApplicationModel();

			if (OP_NEXT.equalsIgnoreCase(op)) {
				pageNo++;
			}
			if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
				pageNo--;
			}

			try {

				HttpSession session = request.getSession();
				UserBean uBean = (UserBean) session.getAttribute("user");

				bean.setRecruiterId(uBean.getId());

				list = model.jobApplicationSearch(bean, pageNo, pageSize);

				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found ", request);
				}
				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				log.error(e);
				ServletUtility.handleException(e, request, response);
				return;
			}
			log.debug("JobListCtl doGet End");
		} catch (Exception e) {
			System.out.println(e);
			log.error(e);
		}
	}

	@Override
	protected String getView() {

		return JPView.JOB_APPLICATION_VIEW;
	}

}
