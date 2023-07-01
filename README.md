
Initial SQL
===========
    CREATE DATABASE connectdotsprj;
    USE connectdotsprj;

Database Diagram
=====

```mermaid
erDiagram
    
    TB_MEMBER||--o{TB_HOTPLACE :""
    TB_HOTPLACE||--o{TB_HOTPLACELIKE :""
    
    TB_MEMBER||--o{TB_FREEBOARD :""
    TB_FREEBOARD||--o{TB_FREEBOARDREPLY : ""
    TB_FREEBOARD||--o{TB_FREEBOARDLIKE : ""
    
    TB_MEMBER||--o{TB_LIVECHAT : ""
    
    TB_MEMBER||--o{TB_AUTH : ""
    
    
    
```
```mermaid
erDiagram
   
    TB_SPOTIFY_MUSIC_PLAYLIST||--|| TB_SPOTIFY_PLAYLIST: ""
    TB_SPOTIFY_MUSIC_PLAYLIST||--|| TB_SPOTIFY_MUSIC: ""
    
    
    TB_CVS
```