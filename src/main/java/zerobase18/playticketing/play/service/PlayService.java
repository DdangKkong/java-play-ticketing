package zerobase18.playticketing.play.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zerobase18.playticketing.global.exception.CustomException;
import zerobase18.playticketing.global.type.ErrorCode;
import zerobase18.playticketing.play.dto.CreatePlay;
import zerobase18.playticketing.play.dto.PlayDto;
import zerobase18.playticketing.play.entity.Play;
import zerobase18.playticketing.play.entity.Schedule;
import zerobase18.playticketing.play.entity.ScheduleAndSeat;
import zerobase18.playticketing.play.repository.PlayRepository;
import zerobase18.playticketing.play.repository.ScheduleAndSeatRepository;
import zerobase18.playticketing.play.repository.ScheduleRepository;
import zerobase18.playticketing.theater.entity.Seat;
import zerobase18.playticketing.theater.entity.Theater;
import zerobase18.playticketing.theater.repository.SeatRepository;
import zerobase18.playticketing.theater.repository.TheaterRepository;

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
    private final ScheduleAndSeatRepository scheduleAndSeatRepository;

    private Theater findTheater(int theaterId) {
        return theaterRepository.findById(theaterId)
                .orElseThrow(() -> new CustomException(ErrorCode.THEATER_NOT_FOUND));
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
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                LocalDate date = LocalDate.parse(String.valueOf(scheduleDate), formatter);

                // 요청받은 integer 형식을 Time 으로 바꿔주기 (scheduleTime)
                if (String.valueOf(scheduleTime).length() != 4) {
                    throw new CustomException(ErrorCode.SCHEDULE_TIME_INVALID);
                }
//                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HHmm");
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime time = LocalTime.parse(String.valueOf(scheduleTime), outputFormatter);
//                String formatTime = localTime.format(outputFormatter);
//                LocalTime time = LocalTime.parse(formatTime, outputFormatter);

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
    private boolean createScheduleAndSeat(Theater theater, List<Schedule> scheduleList){
        List<ScheduleAndSeat> scheduleAndSeats = new ArrayList<>();
        List<Seat> seatList = seatRepository.findAllByTheater(theater);
        for (int i = 0; i < scheduleList.size(); i++) {
            Schedule schedule = scheduleList.get(i);
            for (int j = 0; j < seatList.size(); j++) {
                Seat seat = seatList.get(j);

                // 극장에 있는 좌석의 수 x 스케줄의 수 만큼 연극스케줄별 좌석 생성
                ScheduleAndSeat scheduleAndSeat = scheduleAndSeatRepository.save(ScheduleAndSeat.builder()
                                                                            .schedule(schedule)
                                                                            .seat(seat)
                                                                            .seatYN(true)
                                                                            .build());
                // 리스트에 추가
                scheduleAndSeats.add(scheduleAndSeat);
            }
        }
        boolean createYN = false;

        // 모두 정상 생성되었는지 확인
        if (scheduleAndSeats.size() == scheduleList.size() * seatList.size()) {
            createYN = true;
        }

        return createYN;
    }

    // 연극, 연극스케줄, 연극스케줄 별 좌석 생성
    public PlayDto createPlay(CreatePlay.Request request) throws ParseException {
        Theater theater = findTheater(request.getTheaterId());
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
        boolean scheduleSeatYN = createScheduleAndSeat(theater, scheduleList);

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

}
