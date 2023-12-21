package com.playwith.play.domain.qna.service;

import com.playwith.play.domain.qna.entity.Qna;
import com.playwith.play.domain.qna.repository.QnaRepository;
import com.playwith.play.domain.user.entity.SiteUser;
import com.playwith.play.global.util.DataNotFoundException;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QnaService {
    private final QnaRepository qnaRepository;

    public void create(String title, String content) {
        Qna qna = Qna
                .builder()
                .title(title)
                .content(content)
                .createdDate(LocalDateTime.now())
                .build();
        this.qnaRepository.save(qna);
    }

    public List<Qna> getList() {
        return this.qnaRepository.findAll();
    }

    public Qna getQna(Long id) {
        Optional<Qna> qna = this.qnaRepository.findById(id);
        if (qna.isPresent()) {
            return qna.get();
        }
        else {
            throw new DataNotFoundException("qna not found");
        }
    }

//    public void modify(Qna qna, String title, String content) {
//        qna.setTitle(title);
//        qna.setContent(content);
//        qna.setModifyDate(LocalDateTime.now());
//        this.qnaRepository.save(qna);
//    }

    public void delete(Qna qna) {
        this.qnaRepository.delete(qna);
    }

    public void deleteById(Long itemId) {
        qnaRepository.deleteById(itemId);
    }

    public void deleteAll(List<Qna> qnaList) {
        this.qnaRepository.deleteAll(qnaList);
    }

    public void deleteSelectedQna(String[] selectedIds) {
        for (String idVal : selectedIds) {
            Long id = Long.parseLong(idVal);
            this.qnaRepository.deleteById(id);
        }
    }


}
