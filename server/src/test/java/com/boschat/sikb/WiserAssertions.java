package com.boschat.sikb;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import javax.mail.internet.MimeMessage;
import java.text.MessageFormat;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class WiserAssertions {

    private final List<WiserMessage> messages;

    private WiserAssertions(List<WiserMessage> messages) {
        this.messages = messages;
    }

    public static WiserAssertions assertReceivedMessage(Wiser wiser) {
        return new WiserAssertions(wiser.getMessages());
    }

    private static Supplier<AssertionError> assertionError(String errorMessage, String... args) {
        return () -> new AssertionError(MessageFormat.format(errorMessage, args));
    }

    private static <T> T unchecked(ThrowingSupplier<T> supplier) {
        try {
            return supplier.get();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    public WiserAssertions from(String from) {
        findFirstOrElseThrow(m -> m.getEnvelopeSender().equals(from),
            assertionError("No message from [{0}] found!", from));
        return this;
    }

    public WiserAssertions to(String to) {
        findFirstOrElseThrow(m -> m.getEnvelopeReceiver().equals(to),
            assertionError("No message to [{0}] found! but [{1}] found", to, messages.get(0).getEnvelopeReceiver()));
        return this;
    }

    public WiserAssertions withSubject(String subject) {
        Predicate<WiserMessage> predicate = m -> subject.equals(unchecked(getMimeMessage(m)::getSubject));
        findFirstOrElseThrow(predicate,
            assertionError("No message with subject [{0}] found!", subject));
        return this;
    }

    public WiserAssertions withContent(String content) {
        findFirstOrElseThrow(m -> {
            ThrowingSupplier<String> contentAsString =
                () -> ((String) getMimeMessage(m).getContent()).trim();
            return content.equals(unchecked(contentAsString));
        }, assertionError("No message with content [{0}] found!", content));
        return this;
    }

    private void findFirstOrElseThrow(Predicate<WiserMessage> predicate, Supplier<AssertionError> exceptionSupplier) {
        messages.stream().filter(predicate)
                .findFirst().orElseThrow(exceptionSupplier);
    }

    private MimeMessage getMimeMessage(WiserMessage wiserMessage) {
        return unchecked(wiserMessage::getMimeMessage);
    }

    interface ThrowingSupplier<T> {

        T get() throws Throwable;
    }
}