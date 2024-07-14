package kitae.spring.library.service;

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
}
