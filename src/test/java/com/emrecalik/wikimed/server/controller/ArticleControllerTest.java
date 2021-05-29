package com.emrecalik.wikimed.server.controller;

import com.emrecalik.wikimed.server.model.request.ArticlePostRequestDto;
import com.emrecalik.wikimed.server.model.response.ApiResponseDto;
import com.emrecalik.wikimed.server.model.response.ArticleResponseDto;
import com.emrecalik.wikimed.server.service.ArticleService;
import com.emrecalik.wikimed.server.service.ArticleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.HashSet;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ArticleControllerTest {

    @Mock
    private ArticleService articleService;

    @InjectMocks
    private ArticleController articleController;

    private ObjectMapper objectMapper = new ObjectMapper();

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(articleController).build();
    }

    @Test
    void saveArticle() throws Exception {
        // given
        final long USER_ID = 1;

        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_TAGGED)
                .build();

        ArticlePostRequestDto articlePostRequestDto = ArticlePostRequestDto.builder()
                .userId(USER_ID)
                .pureArticles(new HashSet<>())
                .wikiItems(new HashSet<>())
                .build();

        Mockito.when(articleService.saveArticle(any(ArticlePostRequestDto.class))).thenReturn(apiResponseDto);

        // when
        ResultActions response = mockMvc.perform(
                post(ArticleController.BASE_URL)
                        .content(objectMapper.writeValueAsString(articlePostRequestDto))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.header").value(ArticleServiceImpl.SUCCESS));
        response.andExpect(jsonPath("$.message").value(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_TAGGED));
        verify(articleService, times(1)).saveArticle(any(ArticlePostRequestDto.class));
    }

    @Test
    void getArticlesByUserId() throws Exception {
        // given
        final long USER_ID = 1;
        final int PAGE_NUM = 5;

        when(articleService.getArticlesByUserId(USER_ID, PAGE_NUM)).thenReturn(new HashMap<>());

        // when
        ResultActions response = mockMvc.perform(
                get(ArticleController.BASE_URL + "/mylist")
                        .param("userId", String.valueOf(USER_ID))
                        .param("pageNum", String.valueOf(PAGE_NUM))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        verify(articleService, times(1)).getArticlesByUserId(USER_ID, PAGE_NUM);
    }

    @Test
    void deleteArticleById() throws Exception {
        // given
        final long ARTICLE_ID = 1;

        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_DELETED)
                .build();

        when(articleService.deleteArticleById(ARTICLE_ID)).thenReturn(apiResponseDto);

        // when
        ResultActions response = mockMvc.perform(
                delete(ArticleController.BASE_URL + "/" + ARTICLE_ID)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.header").value(ArticleServiceImpl.SUCCESS));
        response.andExpect(jsonPath(".message").value(ArticleServiceImpl.ARTICLE_SUCCESSFULLY_DELETED));
        verify(articleService, times(1)).deleteArticleById(ARTICLE_ID);
    }

    @Test
    void getUserArticleById() throws Exception {
        // given
        final long ARTICLE_ID = 1;
        final long USER_ID = 5;

        ArticleResponseDto articleResponseDto = ArticleResponseDto.builder()
                .id(ARTICLE_ID)
                .build();

        when(articleService.getUserArticleById(ARTICLE_ID, USER_ID)).thenReturn(articleResponseDto);

        // when
        ResultActions response = mockMvc.perform(
                get(ArticleController.BASE_URL + "/" + ARTICLE_ID)
                        .param("userId", String.valueOf(USER_ID))
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.id").value(ARTICLE_ID));
        verify(articleService, times(1)).getUserArticleById(ARTICLE_ID, USER_ID);
    }

    @Test
    void getAllArticles() throws Exception {
        // given
        final int PAGE_NUM = 5;
        final long USER_ID = 1;

        when(articleService.getPaginatedArticles(PAGE_NUM, USER_ID)).thenReturn(new HashMap<>());

        // when
        ResultActions response = mockMvc.perform(
                get(ArticleController.BASE_URL + "/all")
                        .param("pageNum", String.valueOf(PAGE_NUM))
                        .param("userId", String.valueOf(USER_ID))
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        response.andExpect(status().isOk());
        verify(articleService, times(1)).getPaginatedArticles(PAGE_NUM, USER_ID);
    }

    @Test
    void deleteArticleTag() throws Exception {
        // given
        final long ARTICLE_ID = 1;
        final String WIKI_ITEM_ENTITY_ID = "Q10";

        ApiResponseDto apiResponseDto = ApiResponseDto.builder()
                .header(ArticleServiceImpl.SUCCESS)
                .message(ArticleServiceImpl.ARTICLE_TAG_SUCCESSFULLY_DELETED)
                .build();

        when(articleService.deleteArticleTag(ARTICLE_ID, WIKI_ITEM_ENTITY_ID)).thenReturn(apiResponseDto);

        // when
        ResultActions response = mockMvc.perform(
                delete(ArticleController.BASE_URL + "/" + ARTICLE_ID + "/delete/" + WIKI_ITEM_ENTITY_ID)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        response.andExpect(status().isOk());
        response.andExpect(jsonPath("$.header").value(ArticleServiceImpl.SUCCESS));
        response.andExpect(jsonPath("$.message").value(ArticleServiceImpl.ARTICLE_TAG_SUCCESSFULLY_DELETED));
        verify(articleService, times(1)).deleteArticleTag(ARTICLE_ID, WIKI_ITEM_ENTITY_ID);
    }
}