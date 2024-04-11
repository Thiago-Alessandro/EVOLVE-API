package net.weg.taskmanager.model.dto.converter.get;

import net.weg.taskmanager.model.dto.converter.Converter;
import net.weg.taskmanager.model.dto.get.GetUserChatDTO;
import net.weg.taskmanager.model.entity.UserChat;
import net.weg.taskmanager.model.enums.Priority;
import net.weg.taskmanager.model.record.PriorityRecord;

import java.util.Collection;

public class PriorityRecordConverter implements Converter<PriorityRecord, Priority> {

    @Override
    public PriorityRecord convertOne(Priority obj) {
        return obj != null ? new PriorityRecord(obj.name(), obj.backgroundColor) : null;
    }

    @Override
    public Collection<PriorityRecord> convertAll(Collection<Priority> objs) {
        return objs != null ? objs.stream().map(this::convertOne).toList() : null;
    }
}
