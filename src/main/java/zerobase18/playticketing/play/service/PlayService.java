package zerobase18.playticketing.play.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.global.type.ErrorCode;
import zerobase18.playticketing.play.dto.CreatePlay;
import zerobase18.playticketing.play.dto.PlayDto;
import zerobase18.playticketing.play.dto.UpdatePlay;
import zerobase18.playticketing.play.entity.Play;
import zerobase18.playticketing.play.entity.Schedule;
import zerobase18.playticketing.play.entity.ScheduleSeat;
import zerobase18.playticketing.play.repository.PlayRepository;
import zerobase18.playticketing.play.repository.ScheduleRepository;
import zerobase18.playticketing.play.repository.ScheduleSeatRepository;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SeatRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;
import zerobase18.playticketing.troupe.entity.Troupe;
import zerobase18.playticketing.troupe.repository.TroupeRepository;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlayService {

    private final PlayRepository playRepository;
    private final TheaterRepository theaterRepository;
    private final SeatRepository seatRepository;
    private final ScheduleRepository scheduleRepository;
    private final ScheduleSeatRepository scheduleSeatRepository;
    private final TroupeRepository troupeRepository;

    private Theater findTheater(int theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new CustomException(ErrorCode.THEATER_INVALID));
    }

    private Troupe findTroupe(int troupeId) {
        return troupeRepository.findById(troupeId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_INVALID));
    }

    private Play findPlay(int playId) {
        return playRepository.findById(playId)
                .orElseThrow(() -> new CustomException(ErrorCode.PLAY_INVALID));
    }

    // 연극스케줄 생성
    private List<Schedule> createSchedule(Play play, int[][] scheduleArray) throws ParseException {
        List<Schedule> scheduleList = new ArrayList<>();
        for (int i = 0; i < scheduleArray.length; i++) {
            for (int j = 1; j < scheduleArray[i].length; j++) {
                int scheduleDate = scheduleArray[i][0];
                int scheduleTime = scheduleArray[i][j];

                // 요청받은 integer 형식을 Date 로 바꿔주기 (scheduleDate)
                if (String.valueOf(scheduleDate).length() != 8) {
                    throw new CustomException(ErrorCode.SCHEDULE_DATE_INVALID);
                }
                // "yyyyMMdd" 형식의 문자열을 "yyyy-MM-dd" 형식으로 변환
                String formattedDateString = String.valueOf(scheduleDate).substring(0, 4) + "-"
                        + String.valueOf(scheduleDate).substring(4, 6) + "-"
                        + String.valueOf(scheduleDate).substring(6);
                // "yyyy-MM-dd" 형식의 LocalDate 로 변환
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(formattedDateString, formatter);

                // 요청받은 integer 형식을 Time 으로 바꿔주기 (scheduleTime)
                if (String.valueOf(scheduleTime).length() != 4) {
                    throw new CustomException(ErrorCode.SCHEDULE_TIME_INVALID);
                }
                // "HHmm" 형식의 문자열을 "HH:mm" 형식으로 변환
                String formattedTimeString = String.valueOf(scheduleTime).substring(0, 2) + ":"
                        + String.valueOf(scheduleTime).substring(2);
                // "HH:mm" 형식의 LocalDate 로 변환
                DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime time = LocalTime.parse(formattedTimeString, formatterTime);


                // 연극 스케줄을 만든다
                Schedule schedule = scheduleRepository.save(Schedule.builder()
                                                    .play(play)
                                                    .scheduleDate(date)
                                                    .scheduleTime(time)
                                                    .build());
                // 리스트에 추가
                scheduleList.add(schedule);

            }
        }

        return scheduleList;
    }

    // 연극스케줄 별 좌석 생성
    private boolean createScheduleSeat(Theater theater, List<Schedule> scheduleList){
        List<ScheduleSeat> scheduleSeats = new ArrayList<>();
        List<Seat> seatList = seatRepository.findAllByTheater(theater);
        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            for (int j = 0; j < seatList.size(); j++) {
                Seat seat = seatList.get(j);

                // 극장에 있는 좌석의 수 x 스케줄의 수 만큼 연극스케줄별 좌석 생성
                ScheduleSeat scheduleSeat = scheduleSeatRepository.save(ScheduleSeat.builder()
                                                                            .schedule(schedule)
                                                                            .seat(seat)
                                                                            .build());
                // 리스트에 추가
                scheduleSeats.add(scheduleSeat);
            }
        }
        boolean createYN = false;

        // 모두 정상 생성되었는지 확인
        if (scheduleSeats.size() == scheduleList.size() * seatList.size()) {
            createYN = true;
        }

        return createYN;
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 생성
    public PlayDto createPlay(CreatePlay.Request request) throws ParseException {
        Troupe troupe = findTroupe(request.getTroupeId());
        Theater theater = findTheater(request.getTheaterId());

        // 연극 생성
        Play play = playRepository.save(Play.builder()
                        .theater(theater)
                        .playName(request.getPlayName())
                        .playDetails(request.getPlayDetails())
                        .posterUrl(request.getPosterUrl())
                        .ratings(request.getRatings())
                        .playGenre(request.getPlayGenre())
                        .playStartDate(request.getPlayStartDate())
                        .playEndDate(request.getPlayEndDate())
                        .runtime(request.getRuntime())
                        .actors(request.getActors())
                        .build());

        // 연극스케줄 생성
        List<Schedule> scheduleList = createSchedule(play, request.getSchedule());

        // 연극스케줄 별 좌석 생성
        boolean scheduleSeatYN = createScheduleSeat(theater, scheduleList);

        // 연극 생성 시간 추가
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime createdAt = LocalDateTime.parse(formattedNow, formatter);
        // 다시 저장
        play.setCreatedAt(createdAt);
        playRepository.save(play);

        // Dto 생성해서 나머지 정보 추가
        PlayDto playDto = PlayDto.fromEntity(play);
        playDto.setScheduleList(scheduleList);
        playDto.setScheduleSeatYN(scheduleSeatYN);
        return playDto;
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 조회
    public PlayDto readPlay(int troupeId, int playId) {
        Troupe troupe = findTroupe(troupeId);
        Play play = findPlay(playId);

        // 연극을 등록한 연극 업체인지 확인




        List<Schedule> scheduleList = scheduleRepository.findAllByPlay(play);
        List<ScheduleSeat> scheduleSeats = new ArrayList<>();
        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            List<ScheduleSeat> seatList = scheduleSeatRepository.findAllBySchedule(schedule);
            scheduleSeats.addAll(seatList);
        }

        PlayDto playDto = PlayDto.fromEntity(play);
        playDto.setScheduleList(scheduleList);
        playDto.setScheduleSeats(scheduleSeats);
        return playDto;
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 수정
    @Transactional
    public PlayDto updatePlay(UpdatePlay.Request request) throws ParseException {
        Play play = findPlay(request.getPlayId());
        Troupe troupe = findTroupe(request.getTroupeId());
        Theater theater = findTheater(request.getTheaterId());

        // 연극을 등록한 연극 업체인지 확인



        // 정보 수정, 스케줄과 스케줄 별 좌석은 모두 삭제 후 재생성
        play.setPlayName(request.getPlayName());
        play.setPlayDetails(request.getPlayDetails());
        play.setPosterUrl(request.getPosterUrl());
        play.setRatings(request.getRatings());
        play.setPlayGenre(request.getPlayGenre());
        play.setPlayStartDate(request.getPlayStartDate());
        play.setPlayEndDate(request.getPlayEndDate());
        play.setRuntime(request.getRuntime());
        play.setActors(request.getActors());
        play.setReservationYN(request.isReservationYN());
        play.setTheater(theater);
        // 수정 시간 넣기
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime updatedAt = LocalDateTime.parse(formattedNow, formatter);
        // 다시 저장
        play.setUpdatedAt(updatedAt);
        playRepository.save(play);

        List<Schedule> scheduleList = scheduleRepository.findAllByPlay(play);
        // 스케줄별 좌석 삭제
        for (int i = 0; i < scheduleList.size(); i++) {
            scheduleSeatRepository.deleteAllBySchedule(scheduleList.get(i));
        }
        // 스케줄 삭제
        scheduleRepository.deleteAllByPlay(play);
        // 스케줄 재생성
        List<Schedule> schedules = createSchedule(play, request.getSchedule());
        // 연극스케줄 별 좌석 재생성
        boolean scheduleSeatYN = createScheduleSeat(theater, schedules);
        // playDto 에 넣어주기
        PlayDto playDto = PlayDto.fromEntity(play);
        playDto.setScheduleList(schedules);
        playDto.setScheduleSeatYN(scheduleSeatYN);
        return playDto;
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 삭제
    // (스케줄과 스케줄 별 좌석은 삭제하고 나머지 데이터는 보관한다, deletedAt 에 데이터가 있으면 프론트에서 보이지 않게 처리한다)
    @Transactional
    public PlayDto deletePlay(int troupeId, int playId) {
        Play play = findPlay(playId);
        Troupe troupe = findTroupe(troupeId);

        // 연극을 등록한 연극 업체인지 확인


        // 삭제 시간 넣기
        LocalDateTime now = LocalDateTime.now();
        // DateTimeFormatter를 사용하여 날짜와 시간 형식을 포맷
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedNow = now.format(formatter);
        // 타입을 LocalDateTime 으로
        LocalDateTime deletedAt = LocalDateTime.parse(formattedNow, formatter);
        // 다시 저장
        play.setDeletedAt(deletedAt);
        playRepository.save(play);

        List<Schedule> scheduleList = scheduleRepository.findAllByPlay(play);
        // 좌석 삭제
        for (int i = 0; i < scheduleList.size(); i++) {
            scheduleSeatRepository.deleteAllBySchedule(scheduleList.get(i));
        }
        // 스케줄 삭제
        scheduleRepository.deleteAllByPlay(play);
        // playDto 생성
        return PlayDto.fromEntity(play);
    }

}
