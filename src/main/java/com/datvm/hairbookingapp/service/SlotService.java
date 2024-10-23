package com.datvm.hairbookingapp.service;

import com.datvm.hairbookingapp.dto.request.SlotCreationRequest;
import com.datvm.hairbookingapp.entity.Slot;
import com.datvm.hairbookingapp.entity.enums.Role;
import com.datvm.hairbookingapp.exception.AppException;
import com.datvm.hairbookingapp.exception.ErrorCode;
import com.datvm.hairbookingapp.repository.BookingRepository;
import com.datvm.hairbookingapp.repository.SlotRepository;
import com.datvm.hairbookingapp.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class SlotService {
    @Autowired
    SlotRepository slotRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    StaffRepository staffRepository;

    public void createSlot(SlotCreationRequest request){
        Slot slot = new Slot();
        slot.setTimeStart(request.getTimeStart());
        slotRepository.save(slot);
    }

    public List<Slot> getAllSlot(){
        List<Slot> slots = slotRepository.findAll();
        if (slots.isEmpty())
            throw new AppException(ErrorCode.EMPTY_SLOT);
        return slots;
    }

    public List<Slot> getUnavailableSlot(LocalDate date){
        List<Slot> list = new ArrayList<>();
        List<Slot> slot = slotRepository.findAll();
        for (int i = 0; i < slot.size(); i++) {
            if (bookingRepository.countBookingInSlot(date, slot.get(i).getId()) == staffRepository.countStylist(Role.STYLIST)){
                list.add(slot.get(i));
            }
        }
        return list;
    }

    public void updateSlotById(Long id ,SlotCreationRequest request){
        Slot slot = slotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPTY_SLOT));
        slot.setTimeStart(request.getTimeStart());
        try{
            slotRepository.save(slot);
        }catch (RuntimeException e){
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
    }

    public void deleteSlotById(Long id){
        Slot slot = slotRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.EMPTY_SLOT));
        try{
            slotRepository.delete(slot);
        }catch (RuntimeException e){
            throw new AppException(ErrorCode.PROCESS_FAILED);
        }
    }
}
