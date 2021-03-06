package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {

    @InjectMocks
    TrelloMapper trelloMapper;

    @Test
    public void testMapToBoards() {
        //Given
        List<TrelloBoardDto> listTrelloBoardDto = new ArrayList<>();
        List<TrelloListDto> trelloListDto = new ArrayList<>();
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("1", "Name", trelloListDto);
        listTrelloBoardDto.add(trelloBoardDto);
        //When
        List<TrelloBoard> mappedTrelloBoard = trelloMapper.mapToBoards(listTrelloBoardDto);

        //Then
        assertEquals("Name", mappedTrelloBoard.get(0).getName());

    }

    @Test
    public void testMapToBoardsDto() {
        //Given
        List<TrelloBoard> listTrelloBoard = new ArrayList<>();
        List<TrelloList> trelloList = new ArrayList<>();
        TrelloBoard trelloBoard = new TrelloBoard("1", "Name", trelloList);
        listTrelloBoard.add(trelloBoard);

        //When
        List<TrelloBoardDto> mappedTrelloBoardDto = trelloMapper.mapToBoardsDto(listTrelloBoard);

        //Then
        assertEquals("Name", mappedTrelloBoardDto.get(0).getName());

    }

    @Test
    public void testMapToList() {
        //Given
        List<TrelloList> list = new ArrayList<>();

        //When
        List<TrelloListDto> mappedTrelloListDto = trelloMapper.mapToListDto(list);

        //Then
        assertEquals(0, mappedTrelloListDto.size());

    }

    @Test
    public void testMapToListDto() {
        //Given
        List<TrelloListDto> list = new ArrayList<>();

        //When
        List<TrelloList> mappedTrelloList = trelloMapper.mapToList(list);

        //Then
        assertEquals(0, mappedTrelloList.size());

    }

    @Test
    public void testMapToCard(){
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto("1", "name", "desc", "1" );

        //When
        TrelloCard trelloCard = trelloMapper.mapToCard(trelloCardDto);

        //Then
        assertEquals("name", trelloCard.getName());
    }

    @Test
    public void testMapToCardDto(){
        //Given
        TrelloCard trelloCard = new TrelloCard("name", "desc", "up", "1" );

        //When
        TrelloCardDto trelloCardDto = trelloMapper.mapToCardDto(trelloCard);

        //Then
        assertEquals("up", trelloCardDto.getDescription());
    }

}

