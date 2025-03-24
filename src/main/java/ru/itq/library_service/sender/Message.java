package ru.itq.library_service.sender;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    private String title;
    private String text;
    private List<String> recipientContacts;

    public String[] getRecipientContactArray() {
        return recipientContacts.toArray(new String[0]);
    }
}
