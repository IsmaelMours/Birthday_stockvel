package com.ismael.birthday_stockvel.birthdaygroup;

import com.ismael.birthday_stockvel.dto.BirthdayGroupDTO;
import com.ismael.birthday_stockvel.dto.CreateBirthdayGroupRequest;
import com.ismael.birthday_stockvel.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("birthday-group")
@RequiredArgsConstructor
public class BirthdayGroupController {

    private final BirthdayGroupService birthdayGroupService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<CreateBirthdayGroupRequest> createBirthdayGroup(@RequestBody CreateBirthdayGroupRequest request) {
        try {
            BirthdayGroup createdGroup = birthdayGroupService.createBirthdayGroup(request.getGroupName(), request.getContributionAmount());
            return ResponseEntity.ok(request);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/members/{memberId}")
    public ResponseEntity<String> removeMemberByOwner(Authentication authentication,
                                                      @PathVariable Long memberId) {
        try {
            birthdayGroupService.removeMemberByOwner(authentication, memberId);
            return ResponseEntity.ok("Member removed successfully.");
        } catch (IllegalArgumentException | AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/groups")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<BirthdayGroupDTO>> getAllGroups() {
        List<BirthdayGroupDTO> groups = birthdayGroupService.listAllGroups();
        return ResponseEntity.ok(groups);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/joined-groups")
    public ResponseEntity<List<BirthdayGroupDTO>> getJoinedGroups(@AuthenticationPrincipal User user) {
        try {
            List<BirthdayGroupDTO> joinedGroups = birthdayGroupService.getJoinedGroups(user.getUserId());
            return ResponseEntity.ok(joinedGroups);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
