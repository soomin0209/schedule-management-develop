package com.schedulemanagementdevelop.user.service;

import com.schedulemanagementdevelop.common.exception.DuplicateEmailException;
import com.schedulemanagementdevelop.common.exception.UserNotFoundException;
import com.schedulemanagementdevelop.common.exception.WrongPasswordException;
import com.schedulemanagementdevelop.schedule.entity.Schedule;
import com.schedulemanagementdevelop.schedule.repository.ScheduleRepository;
import com.schedulemanagementdevelop.user.config.PasswordEncoder;
import com.schedulemanagementdevelop.user.dto.*;
import com.schedulemanagementdevelop.user.entity.User;
import com.schedulemanagementdevelop.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ScheduleRepository scheduleRepository;

    // 유저 생성 (회원가입)
    @Transactional
    public SignupUserResponse save(@Valid SignupUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new DuplicateEmailException("이미 사용 중인 이메일입니다.");
        }
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        User user = new User(request.getName(), request.getEmail(), encodedPassword);
        User savedUser = userRepository.save(user);
        return new SignupUserResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getModifiedAt()
        );
    }

    // 로그인
    @Transactional(readOnly = true)
    public SessionUser login(@Valid LoginUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new WrongPasswordException("비밀번호가 일치하지 않습니다");
        }
        return new SessionUser(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }

    // 유저 전체 조회
    @Transactional(readOnly = true)
    public List<GetUsersResponse> findAll() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> new GetUsersResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail(),
                        user.getCreatedAt(),
                        user.getModifiedAt()
                )).toList();
    }

    // 유저 단건 조회
    @Transactional(readOnly = true)
    public GetUserResponse findOne(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        // 해당 유저가 등록한 일정 조회
        List<Schedule> schedules = scheduleRepository.findByUserIdOrderByModifiedAtDesc(userId);
        List<GetUserSchedulesResponse> userSchedules = schedules.stream()
                .map(schedule -> new GetUserSchedulesResponse(
                        schedule.getTitle(),
                        schedule.getModifiedAt()
                )).toList();
        return new GetUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                userSchedules
        );
    }

    // 유저 수정 (회원정보수정)
    @Transactional
    public UpdateUserResponse update(Long userId, @Valid UpdateUserRequest request) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("없는 유저입니다.")
        );
        user.update(request.getName());
        return new UpdateUserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    // 유저 삭제 (회원탈퇴)
    @Transactional
    public void delete(Long userId) {
        boolean existence = userRepository.existsById(userId);
        if (!existence) {
            throw new UserNotFoundException("없는 유저입니다.");
        }
        userRepository.deleteById(userId);
    }
}
