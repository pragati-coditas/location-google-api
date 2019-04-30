package com.coditas.controller.service;

import com.coditas.controller.dto.ActivityDTO;
import com.coditas.controller.entity.Activity;
import com.coditas.controller.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Harshal Patil
 */

@Service
public class ActivityService {

    @Autowired
    ActivityRepository activityRepository;

    @Transactional
    public List<ActivityDTO> findByUserEmail(String email, int noOfRecords) {
        List<ActivityDTO> dtos = new ArrayList<>();
        List<Activity> activities = activityRepository.findByUserEmail(email, PageRequest.of(0, noOfRecords, Sort.by("createdDate").descending()));
        ActivityDTO activityDTO;
        for(Activity activity: activities) {
            activityDTO = new ActivityDTO();

            activityDTO.setUpc(activity.getProduct().getUpc());
            activityDTO.setCreatedDate(activity.getCreatedDate());
            activityDTO.setBottomFacing(activity.getBottomFacing());
            activityDTO.setFrontFacing(activity.getFrontFacing());
            activityDTO.setLeftFacing(activity.getLeftFacing());
            activityDTO.setRearFacing(activity.getRearFacing());
            activityDTO.setRightFacing(activity.getRightFacing());
            activityDTO.setTopFacing(activity.getTopFacing());
            activityDTO.setTotalPhotosCount(activity.getTotalPhotosCount());
            activityDTO.setPriceImage(activity.getPriceImage());

            dtos.add(activityDTO);
        }
        return dtos;
    }
}
