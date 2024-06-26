package com.ismael.birthday_stockvel.contribution;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("contributions")
@RequiredArgsConstructor
public class ContributionController {

    private final ContributionService contributionService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/contribute/{groupId}")
    public ResponseEntity<String> contributeToBirthdayMember(@PathVariable Long groupId,
                                                             Authentication authentication) {
        try {
            contributionService.contributeToBirthdayMember(authentication, groupId);
            return ResponseEntity.ok("Contribution successful.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
