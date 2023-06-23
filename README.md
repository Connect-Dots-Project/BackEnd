```mermaid
erDiagram
    TB_MEMBER{
        INT(10) member_idx PK "AUTO INCREMENT"
        VARCHAR(50) member_account "NOT NULL, UNIQUE KEY"
        VARCHAR(2000) member_password "NOT NULL"
        VARCHAR(30) member_name "NOT NULL"
        VARCHAR(50) member_nickname "NOT NULL, UNIQUE KEY"
        VARCHAR(1) member_gender "NOT NULL"
        VARCHAR(50) member_profile
        DATE member_birth 
        DATE member_sign_date "DEFAULT CURRENT_TIMESTAMP"
        VARCHAR(15) member_phone "NOT NULL, UNIQUE KEY"
        VARCHAR(20) member_location "NOT NULL"
        VARCHAR(50) member_comment "NOT NULL"
        VARCHAR(10) member_login_method "NOT NULL"
    }
    
    TB_MEMBER||--o{TB_HOTPLACE :""
    TB_HOTPLACE{
        INT(10) hotplace_idx PK "AUTO INCREMENT"
        VARCHAR(200) hotplace_img "NOT NULL"
        VARCHAR(100) hotplace_content "NOT NULL"
        VARCHAR(20) hotplace_location "NOT NULL"
        DATE hotplace_write_date "DEFAULT CURRENT_TIMESTAMP"
        INT(10) hotplace_like_count "DEFAULT 0"
        VARCHAR(20) hotplace_latitude "NOT NULL"
        VARCHAR(20)  hotplace_longitude "NOT NULL"
        VARCHAR(20) hotplace_name "NOT NULL"
        VARCHAR(50) hotplace_full_address "NOT NULL"
        INT(10) member_idx "NOT NULL"
    }
    
    TB_MEMBER||--o{TB_FREEBOARD :""
    TB_FREEBOARD{
        INT(10) free_board_idx PK "AUTO INCREMENT"
        VARCHAR(200) free_board_img
        VARCHAR(50) free_board_title "NOT NULL"
        VARCHAR(2000) freeboard_content "NOT NULL"
        VARCHAR(20) freeboard_location "NOT NULL"
        VARCHAR(20) freeboard_category "NOT NULL"
        TIMESTAMP freeboard_write_date "DEFAULT CURRENT_TIMESTAMP"
        TIMESTAMP freeboard_update_date "DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP"
        INT(10) free_board_view_count "DEFAULT 0"
        INT(10) free_board_reply_count "DEFAULT 0"
        INT(10) free_board_like_count "DEFAULT 0"
        INT(10) member_idx "NOT NULL"
    }
    TB_FREEBOARD||--o{TB_FREEBOARDREPLY : ""
%%    TB_FREEBOARDREPLY||--||TB_MEMBER : ""
    TB_FREEBOARDREPLY{
        INT(10) free_board_reply_idx PK "AUTO INCREMENT"
        TIMESTAMP free_board_reply_create_date "DEFAULT CURRENT_TIMESTAMP"
        VARCHAR(500) free_board_reply_content"NOT NULL"
        INT(10) member_idx "NOT NULL"
        INT(10) free_board_idx "NOT NULL"
    }
    
    
   
    
%%    TB_CART{
%%        INT(10) cart_idx "AUTO_INCREMENT"
%%        INT(10) item_img "NOT NULL"
%%        INT(10) item_title   "NOT NULL"
%%        VARCHAR(10) item_price  "NOT NULL"
%%        VARCHAR(5) item_sale  "NOT NULL"
%%        INT(2) item_amount  "NOT NULL"
%%        INT(10) member_idx "NOT NULL"
%%    }
    
 
    
    TB_MEMBER||--o{TB_LIVECHAT : ""
    TB_LIVECHAT{
        INT(10) livechat_idx  PK "AUTO_INCREMENT"
        VARCHAR(500) livechat_content  "NOT NULL"
        VARCHAR(20) livechat_hashtag "NOT NULL"
        DATE livechat_create_date "DEFAULT CURRENT_TIMESTAMP"
        INT(10) member_idx  "UNIQUE"
    }
    
    
```
```mermaid
erDiagram
    TB_SPOTIFY_MUSIC{
        INT(10) music_board_idx PK "AUTO_INCREMENT"
        VARCHAR(50) spotify_music_id "NOT NULL"
        VARCHAR(1000) music_board_title "NOT NULL"
        VARCHAR(50) music_board_artist  "NOT NULL"
        VARCHAR(2000) music_board_title_image  "NOT NULL"
        VARCHAR(2000) music_board_preview_url
    }
%%    TB_SPOTIFY_MUSIC||--o{TB_SPOTIFY_PLAYLIST:""
    TB_SPOTIFY_PLAYLIST{
        INT(10) music_board_idx PK "AUTO_INCREMENT"
        VARCHAR(50) music_board_playlist_id "NOT NULL"
        VARCHAR(50) music_board_track "NOT NULL"
        VARCHAR(2000) music_board_track_image  "NOT NULL"
        INT(20) music_board_view_count "DEFAULT 0"
    }
    TB_SPOTIFY_MUSIC_PLAYLIST||--|| TB_SPOTIFY_PLAYLIST: ""
    TB_SPOTIFY_MUSIC_PLAYLIST||--|| TB_SPOTIFY_MUSIC: ""


    TB_SPOTIFY_MUSIC_PLAYLIST{
        INT(10) music_board_idx PK "AUTO_INCREMENT"
        INT(10) spotify_playlist_id FK "NOT NULL"
        INT(10) spotify_music_id FK "NOT NULL"
    }
```
````mermaid
erDiagram
    TB_CVS{
        INT(10) cvs_idx PK "AUTO_INCREMENT"
        VARCHAR(200) cvs_img "NOT NULL"
        VARCHAR(50) cvs_title "NOT NULL"
        VARCHAR(10) cvs_price "NOT NULL"
        VARCHAR(5) cvs_sale "NOT NULL"
        VARCHAR(10) cvs_type "NOT NULL"
        DATE cvs_reg_date "DEFAULT CURRENT_TIMESTAMP"
    }
````
```mermaid
erDiagram
    TB_AUTH {
        INT(10) auth_idx PK "AUTO_INCREMENT"
        VARCHAR(1500) refreshToken "NOT NULL"
        DATE auth_date  "DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP"
        VARCHAR(50) account "UNIQUE"
    }
```