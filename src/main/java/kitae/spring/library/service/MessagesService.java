package kitae.spring.library.service;

import kitae.spring.library.dto.AdminQuestionRequest;
import kitae.spring.library.entity.Message;
import kitae.spring.library.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MessagesService {

    private final MessageRepository messageRepository;

    public void postMessage(Message messageRequest, String userEmail) {
        Message message = Message.builder()
                .title(messageRequest.getTitle())
                .question(messageRequest.getQuestion())
                .userEmail(userEmail)
                .build();

        messageRepository.save(message);
    }

    public void putMessage(AdminQuestionRequest adminQuestionRequest, String userEmail) {
        Message message = messageRepository.findById(adminQuestionRequest.getId()).orElseThrow(
                () -> new IllegalArgumentException("해당 메시지가 존재하지 않습니다.")
        );

        message.setAdminEmail(userEmail);
        message.setResponse(adminQuestionRequest.getResponse());
        message.setClosed(true);

        messageRepository.save(message);

    }
}
