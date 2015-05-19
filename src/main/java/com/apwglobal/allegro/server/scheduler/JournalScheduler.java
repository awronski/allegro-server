package com.apwglobal.allegro.server.scheduler;

import com.apwglobal.allegro.server.service.IAllegroClientFactory;
import com.apwglobal.allegro.server.service.IJournalService;
import com.apwglobal.nice.service.IAllegroNiceApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class JournalScheduler {

    @Autowired
    private IAllegroClientFactory allegro;

    @Autowired
    private IJournalService journalService;

    @Scheduled(fixedDelay=5 * 60000)
    @Transactional
    public void syncJournal() {
        allegro
                .getAll()
                .forEach(this::syncJournalForGivenClient);
    }

    private void syncJournalForGivenClient(IAllegroNiceApi client) {
        Optional<Long> lastRawId = journalService.findLastRowId(client.getClientId());

        client
                .login()
                .getJournal(lastRawId.orElse(0l))
                .forEach(journalService::saveJournal);
    }

}
