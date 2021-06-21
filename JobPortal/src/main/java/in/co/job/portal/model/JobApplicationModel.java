package in.co.job.portal.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import in.co.job.portal.bean.JobApplicationBean;
import in.co.job.portal.bean.JobBean;
import in.co.job.portal.bean.SendMailBean;
import in.co.job.portal.bean.UserBean;
import in.co.job.portal.exception.ApplicationException;
import in.co.job.portal.exception.DatabaseException;
import in.co.job.portal.exception.DuplicateRecordException;
import in.co.job.portal.exception.RecordNotFoundException;
import in.co.job.portal.util.EmailBuilder;
import in.co.job.portal.util.EmailMessage;
import in.co.job.portal.util.JDBCDataSource;
import in.co.job.portal.util.JobSendMail;


public class JobApplicationModel {


	private static Logger log = Logger.getLogger(JobApplicationModel.class);
	
	
	public Integer nextPK() throws DatabaseException {
		log.debug("Model nextPK Started");
		Connection conn = null;
		int pk = 0;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ID) FROM P_applay");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				pk = rs.getInt(1);
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new DatabaseException("Exception : Exception in getting PK");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model nextPK End");
		return pk + 1;
	}
	
	
	public JobApplicationBean findByPK(long pk) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("SELECT * FROM P_applay where id=?");
		
		JobApplicationBean bean = null;
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new JobApplicationBean();
				bean.setId(rs.getLong(1));
				bean.setRecruiterId(rs.getLong(2));
				bean.setJobId(rs.getLong(3));
				bean.setUserId(rs.getLong(4));
				bean.setApplyDate(rs.getTimestamp(5));
				bean.setUserName(rs.getString(6));
				bean.setResumeFile(rs.getString(7));
				bean.setProcessed(rs.getString(8)+"");
			}
			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in getting User by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model findByPK End");
		return bean;
		
		
		
	}


	public List jobApplicationSearch(JobApplicationBean bean) throws ApplicationException {
		return jobApplicationSearch(bean, 0, 0);
	}


	public List jobApplicationSearch(JobApplicationBean bean, int pageNo, int pageSize) throws ApplicationException {
		log.debug("Model search Started");
		StringBuffer sql = new StringBuffer("SELECT * FROM P_applay where recruiterid=?");

		 sql.append(" Order by ID asc");
		// if page size is greater than zero then apply pagination
		if (pageSize > 0) {
			// Calculate start record index
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" Limit " + pageNo + ", " + pageSize);
			// sql.append(" limit " + pageNo + "," + pageSize);
		}
		ArrayList list = new ArrayList();
		Connection conn = null;
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, bean.getRecruiterId());

			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				bean = new JobApplicationBean();

				bean.setId(rs.getLong(1));
				bean.setRecruiterId(rs.getLong(2));
				bean.setJobId(rs.getLong(3));
				bean.setUserId(rs.getLong(4));
				bean.setApplyDate(rs.getTimestamp(5));
				bean.setUserName(rs.getString(6));
				bean.setResumeFile(rs.getString(7));
				bean.setProcessed(rs.getString(8)+"");

				System.out.println("jobapp: "+bean);
				list.add(bean);
				
			}
			
			//list.forEach(System.out::println);

			rs.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			throw new ApplicationException("Exception : Exception in search Role");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model search End");
		return list;
	}
	
	public void updateJobApplicationStatus(int id) throws ApplicationException, DuplicateRecordException {
		log.debug("Model update Started");
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false); // Begin transaction
			PreparedStatement pstmt = conn.prepareStatement(
					"UPDATE p_applay SET Processed=? WHERE ID=?");
			pstmt.setString(1, "Y");
			pstmt.setLong(2, id);
			pstmt.executeUpdate();
			conn.commit(); 
			pstmt.close();
		} catch (Exception e) {
			log.error("Database Exception..", e);
			try {
				conn.rollback();
			} catch (Exception ex) {
				throw new ApplicationException("Exception : Delete rollback exception " + ex.getMessage());
			}
			throw new ApplicationException("Exception in updating Role ");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		log.debug("Model update End");
	}
	
	
	
}
