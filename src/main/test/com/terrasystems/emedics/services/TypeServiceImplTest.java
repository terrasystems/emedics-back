package com.terrasystems.emedics.services;

import com.terrasystems.emedics.dao.TypeRepository;
import com.terrasystems.emedics.enums.MessageEnums;
import com.terrasystems.emedics.enums.UserType;
import com.terrasystems.emedics.model.Types;
import com.terrasystems.emedics.model.dtoV2.CriteriaDto;
import com.terrasystems.emedics.model.dtoV2.ResponseDto;
import com.terrasystems.emedics.model.dtoV2.TypeDto;
import com.terrasystems.emedics.model.mapping.TypeMapper;
import com.terrasystems.emedics.utils.Utils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TypeServiceImplTest {

    @InjectMocks
    TypeServiceImpl typeServiceImpl;

    @Mock
    Utils utils;
    @Mock
    TypeRepository typeRepository;

    @Test
    public void test_will_return_type_is_null() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_TYPE_IS_NULL.toString(), null);
        CriteriaDto criteriaDto = new CriteriaDto();

        when(utils.generateResponse(false, MessageEnums.MSG_TYPE_IS_NULL.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = typeServiceImpl.getAllTypes(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_all_types() {
        List<TypeDto> types = new ArrayList<>();
        List<Types> types1 = new ArrayList<>();
        CriteriaDto criteriaDto = new CriteriaDto();
        criteriaDto.setType(UserType.DOCTOR);
        ResponseDto responseDto = new ResponseDto(true, criteriaDto.getType().toString().toLowerCase() + " types", types);

        when(utils.generateResponse(true, criteriaDto.getType().toString().toLowerCase() + " types", types)).thenReturn(responseDto);
        when(typeRepository.findByUserType(criteriaDto.getType())).thenReturn(types1);

        ResponseDto responseDto1 = typeServiceImpl.getAllTypes(criteriaDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_getType() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(typeRepository.findOne("")).thenReturn(null);

        ResponseDto responseDto1 = typeServiceImpl.getType("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_type_by_id() {
        Types type = new Types();
        TypeMapper mapper = TypeMapper.getInstance();
        TypeDto typeDto = mapper.toDto(type);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TYPE_BY_ID.toString(), typeDto);

        when(utils.generateResponse(true, MessageEnums.MSG_TYPE_BY_ID.toString(), typeDto)).thenReturn(responseDto);
        when(typeRepository.findOne("")).thenReturn(type);

        ResponseDto responseDto1 = typeServiceImpl.getType("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_removeType() {
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);
        when(typeRepository.findOne("")).thenReturn(null);

        ResponseDto responseDto1 = typeServiceImpl.removeType("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_remove_type_by_id() {
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TYPE_REMOVED.toString(), null);
        Types types = new Types();

        when(utils.generateResponse(true, MessageEnums.MSG_TYPE_REMOVED.toString(), null)).thenReturn(responseDto);
        when(typeRepository.findOne("")).thenReturn(types);

        ResponseDto responseDto1 = typeServiceImpl.removeType("");

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_return_incorrect_request_for_createType() {
        TypeDto typeDto = new TypeDto();
        ResponseDto responseDto = new ResponseDto(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null);

        when(utils.generateResponse(false, MessageEnums.MSG_REQUEST_INCORRECT.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = typeServiceImpl.createType(typeDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }

    @Test
    public void test_will_create_type() {
        TypeDto typeDto = new TypeDto();
        typeDto.setName("buba");
        typeDto.setUserType(UserType.DOCTOR);
        ResponseDto responseDto = new ResponseDto(true, MessageEnums.MSG_TYPE_CREATED.toString(), null);

        when(utils.generateResponse(true, MessageEnums.MSG_TYPE_CREATED.toString(), null)).thenReturn(responseDto);

        ResponseDto responseDto1 = typeServiceImpl.createType(typeDto);

        assertEquals(responseDto.getResult(), responseDto1.getResult());
        assertEquals(responseDto.getState(), responseDto1.getState());
        assertEquals(responseDto.getMsg(), responseDto1.getMsg());
    }
}
