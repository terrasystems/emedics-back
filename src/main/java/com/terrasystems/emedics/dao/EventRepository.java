package com.terrasystems.emedics.dao;


import com.terrasystems.emedics.enums.StatusEnum;
import com.terrasystems.emedics.model.Event;
import com.terrasystems.emedics.model.Template;
import com.terrasystems.emedics.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface EventRepository extends JpaSpecificationExecutor<Event>, JpaRepository<Event, String> {
    /*List<Event> findByToUser_IdAndStatus(String id, StatusEnum status);
    *//*boolean existsByPatient_IdAndStatusAndTemplate_Id(String patientId, StatusEnum status, String templateI);
    boolean existsByToUser_IdAndStatusAndTemplate_Id(String patientId, StatusEnum status, String templateId);*//*
    List<Event> findByFromUser_IdAndStatus(String id, StatusEnum status);
    List<Event> findByStatusAndFromUser_IdOrToUser_Id(StatusEnum status, String fromUserId, String toUserId);
    Long countByFromUser_IdAndTemplate_IdAndStatus (String userId, String templateId, StatusEnum status);
    Long countByToUser_IdAndTemplate_IdAndStatus(String userId, String templateId, StatusEnum status);
    List<Event> findByFromUser_Id(String userId);
    List<Event> findDistinctByPatient_IdAndTemplate_IdAndStatusIsNotAndFromUser_IdOrPatient_IdAndTemplate_IdAndStatusIsNotAndToUser_Id(String patientId, String templateId, StatusEnum status, String fromUser, String patientId2, String templateId2, StatusEnum status2, String toUser);

    @Query("SELECT DISTINCT e.template.id from Event as e where e.patient.id=:patientId")
    List<String> findTemplate_IdByPatient_Id(@Param("patientId") String patientId);

    List<Event> findByFromUser_IdAndTemplate_IdAndStatus(String patientId, String templateId, StatusEnum status);
    @Query("SELECT DISTINCT e.template.id from Event as e where e.fromUser.id=:stuffId")
    List<String> findTemplate_IdByStuff_Id(@Param("stuffId") String stuffId);


   *//* List<Event> findByTemplate_IdAndStatusAndFromUser_Id(String templateId, StatusEnum statusEnum, String fromUserId);
    List<Event> findByTemplate_IdAndStatusAndToUser_Id(String templateId, StatusEnum statusEnum, String toUserId);
    List<Event> findByFromUser_IdAndToUser_IdAndStatus(String fromId, String toId, StatusEnum statusEnum);
    List<Event> findByPatient_IdAndToUser_IdAndStatus(String patientId, String toId, StatusEnum statusEnum);*//*
    List<Event> findByDateBetween(Date date, Date date2);
    List<Event> findByTemplate_IdAndFromUser_IdAndPatient_IdAndDateBetween(String templateId, String fromUserId, String userId, Date dateFrom, Date dateTo);
    */
    List<Event> findByPatient_Id_AndStatus(String patientId, StatusEnum status);
    List<Event> findByFromUser_IdAndStatus(String fromUserId, StatusEnum status);
    Long countByFromUserAndTemplateAndStatus(User fromUser, Template template, StatusEnum statusEnum);
    Long countByToUserAndTemplateAndStatus(User toUser, Template template, StatusEnum statusEnum);
    List<Event> findByToUser_IdAndStatusAndTemplate_NameContainingIgnoreCaseOrToUser_IdAndStatusAndFromUser_NameContainingIgnoreCase(String userId, StatusEnum statusEnum, String templateName,String UserId2, StatusEnum statusEnum2, String fromUserName);

}
