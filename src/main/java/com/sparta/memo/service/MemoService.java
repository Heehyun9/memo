package com.sparta.memo.service;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import com.sparta.memo.repository.MemoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemoService {

    private final MemoRepository memoRepository;

//    public MemoService(ApplicationContext context){
//        //1. 'Bean'이름으로 가져오기
//        MemoRepository memoRepository = (MemoRepository)context.getBean("memoRepository");
//
//        //2. 'Bean' 클래스 형식으로 가져오기
//        MemoRepository memoRepository = context.getBean(MemoRepository.class);
//
//        this.memoRepository = memoRepository;
//    }

    //Bean 주입 방법 4가지 (1번 추천)
    //1. 생성자 오버로딩으로 주입
    public MemoService(MemoRepository memoRepository) {
        this.memoRepository = memoRepository;
    }
    //2. 클래스 위에 @RequiredArgsConstructor로 주입
    //3. MemoRepository 위에 @Autowired
    //4. 메서드 주입도 가능 (위에 @Autowired)

    public MemoResponseDto createMemo(MemoRequestDto requestDto) {
        // RequestDto -> Entity
        Memo memo = new Memo(requestDto);
        // DB 저장
        Memo saveMemo = memoRepository.save(memo);
        // Entity -> ResponseDto
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;

    }

    public List<MemoResponseDto> getMemos() {

        // DB 조회
        return memoRepository.findAll();

    }

    public Long updateMemo(Long id, MemoRequestDto requestDto) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);


        if(memo != null) {
            // memo 내용 수정
            memoRepository.update(id,requestDto);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }

    public Long deleteMemo(Long id) {

        // 해당 메모가 DB에 존재하는지 확인
        Memo memo = memoRepository.findById(id);
        if(memo != null) {
            // memo 삭제
            memoRepository.delete(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 메모는 존재하지 않습니다.");
        }
    }



}
