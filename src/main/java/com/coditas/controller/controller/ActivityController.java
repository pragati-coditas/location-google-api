package com.coditas.controller.controller;

import com.coditas.controller.service.ActivityService;
import com.coditas.controller.dto.ActivityDTO;
import com.coditas.controller.security.UserPrincipal;
import com.coditas.controller.utils.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Harshal Patil
 */
@RestController
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    ActivityService activityService;

    @GetMapping("")
    public ApiResponse getUserActivity(@RequestParam int size) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         ApiResponse apiResponse = new ApiResponse(true);
        if (null == userPrincipal) {
            return apiResponse.setError("Unauthorized access","401");
        }

        List<ActivityDTO> activityDTOS = activityService.findByUserEmail(userPrincipal.getEmail(), size);

        if(null == activityDTOS || activityDTOS.isEmpty()) {
            apiResponse.setMessage("No Data found");
        } else {
            apiResponse.addData("activities", activityDTOS);
        }

        return apiResponse;
    }
}
