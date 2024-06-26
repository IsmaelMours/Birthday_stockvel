package com.ismael.birthday_stockvel.groupmember;

import com.ismael.birthday_stockvel.birthdaygroup.BirthdayGroupService;
import com.ismael.birthday_stockvel.dto.GroupJoinRequestDTO;
import com.ismael.birthday_stockvel.dto.GroupMemberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("member")
@RequiredArgsConstructor
public class GroupMemberController {

    private final GroupMemberService groupMemberService;

    @PostMapping("/join/{groupId}")
    public ResponseEntity<String> joinGroup(@PathVariable Long groupId,
                                            Authentication authentication) {
        try {
            groupMemberService.joinGroup(authentication, groupId);
            return ResponseEntity.ok("Successfully joined the group.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping("/my-groups/members")
    public ResponseEntity<List<GroupMemberDTO>> getMyGroupMembers(Authentication authentication) {
        try {
            List<GroupMemberDTO> groupMembers = groupMemberService.getGroupMembers(authentication);
            return ResponseEntity.ok(groupMembers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        }
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/requests/{requestId}/approve")
    public ResponseEntity<String> approveJoinRequest(@PathVariable Long requestId) {
        groupMemberService.approveJoinRequest(requestId);
        return ResponseEntity.ok("Join request approved successfully.");
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/requests/{requestId}/reject")
    public ResponseEntity<String> rejectJoinRequest(@PathVariable Long requestId) {
        groupMemberService.rejectJoinRequest(requestId);
        return ResponseEntity.ok("Join request rejected successfully.");
    }

    @GetMapping("/owner/join-requests")
    public ResponseEntity<List<GroupJoinRequestDTO>> getJoinRequestsForOwner(Authentication authentication) {
        List<GroupJoinRequestDTO> joinRequests = groupMemberService.getJoinRequestsForOwner(authentication);
        return ResponseEntity.ok(joinRequests);
    }
}
