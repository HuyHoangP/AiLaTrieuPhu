# Ai là triệu phú 

Dive into a world of tricky questions with 'Who Wants to Be a Millionaire'. Test your knowledge, compete best score with friends, and become the ultimate champion!<br/>
If you guys want to pull this and give ti a try, use this account:
- Username: user1@gmail.com
- Password: test123
<br/>
<div align="center">
   <img width="296" alt="MenuScreen" src="https://github.com/user-attachments/assets/58b62cee-e82b-4476-bdc7-b183db0d83c8">
   <img width="295" alt="PlayScreen" src="https://github.com/user-attachments/assets/e3c9c3cd-2351-4504-837f-e667c79b6eab">
</div>

## Features

### 1. Load songs from your phone
Users touch the gear icon on top-right to go to setting screen. Then choose scan music options to load song from your phone. They can also move to the photos main screen from here<br/>
- There is a toast will pop-up when load song operation is complete
- I used "Cursor" interface to query audio flie in MediaStore External Storage, then save them to local database
<div align="center"><b>SETTINGS SCREEN</b></div><br/>
<div align="center">
   <img width="294" alt="SettingScreen" src="https://github.com/user-attachments/assets/fb407959-8576-4168-bd07-a412d6238999">
</div>

### 2. Search Functionality
Users touch the search icon next to gear icon on top-right to go to search screen. Quickly search for songs, albums, or artists by using the built-in search bar. Results are displayed in real-time as you type.<br/>
- I used SearchView and RecycleView to do this function
<div align="center"><b>SEARCH SCREEN</b></div><br/>
<div align="center">
   <img width="287" alt="SearchScreen" src="https://github.com/user-attachments/assets/8a1b5a0c-9f53-4832-acf6-39dd131b1033">
</div>

### 3. Playlist Creation
Create custom playlists by selecting tracks from the library. Playlists can be saved for future playback, edited, or deleted.<br/>
- Users can switch between add or delete mode on config dialog
- I used SharedPreference to save users' songs
<div align="center"><b>PLAYLIST SCREEN</b></div><br/>
<div align="center">
   <img width="297" alt="PlaylistScreen" src="https://github.com/user-attachments/assets/01140497-1ff0-4e37-a680-277761866f82">
   <img width="298" alt="ConfigDialog" src="https://github.com/user-attachments/assets/f13b5f50-4c5d-47a0-b2d4-216d3811bb9c">
</div>



### 4. Auto-filter song
Auto-filter song by artist and album when the app starts<br/>
- I also used "Swipe refresh layout" in case users want to use this function manually

<div align="center"><b>CATEGORY SCREEN</b></div><br/>
<div align="center">
   <img width="290" height="630" alt="ArtistScreen" src="https://github.com/user-attachments/assets/25439f13-b3ab-4750-a776-b96f113ca410">
   <img width="295" height="630" alt="AlbumScreen" src="https://github.com/user-attachments/assets/5312245f-38c1-430b-b5f8-49ea491d1fa9">
</div>
  


### 5. Music Playback
The app provides music playback with features such as play, pause, skip, shuffle, and repeat. The current track, album cover, and playback controls are shown on the now-playing screen.

<div align="center"><b>BOTTOM CONTROLLER AND PLAYING SCREEN</b></div><br/>
<div align="center">
   <img width="295" alt="MiniController" src="https://github.com/user-attachments/assets/bb600d20-f237-4a18-bf06-f9789f379d19">
   <img width="290" alt="PlayingScreen" src="https://github.com/user-attachments/assets/0d98b0ef-ee75-4a75-80e3-a3572448d0db">
</div>


### 6. Notification
Obviously, a music listening app cannot lack a notification. Users will use this notification to control the music player while the app is in the background.

<div align="center"><b>NOTIFICATION</b></div><br/>
<div align="center">
  <img width="300" alt="Notification" src="https://github.com/user-attachments/assets/add5996e-b098-486b-a2c7-b15451258f29">
</div>


### 7. Auto-change theme color
Now if you notice in the illustrations above, the color of the app is different, right? Yes, the app's theme color will automatically change gradually according to the current song's thumbnail. This also apply to notification's color. Enjoy your music in a visually pleasing environment.
- I used Pallete library to do this function
<div align="center"><b>CHANGE THEME COLOR</b></div><br/>
<div align="center">
   <img width="301" alt="MemoryScreen" src="https://github.com/user-attachments/assets/d6012f76-8181-4c9c-975e-ed667c8ec7a9">
</div>



### 8. Manage your memories
Create your memory by select at least 1 photo in your gallery. Then users can add, delete, remame memory or photo inside that memory. You can change memory's cover image as well 
<div align="center"><b>PHOTO MAIN SCREEN AND MEMORY SCREEN</b></div><br/>
<div align="center">
   <img width="297" alt="PhotoMainScreen2" src="https://github.com/user-attachments/assets/d04157ea-dfcf-4eb5-b6bb-3dd7a689d23e">
   <img width="301" alt="MemoryScreen" src="https://github.com/user-attachments/assets/9f7e28af-6488-4fa3-894a-3a2f31a98ea5">
</div>

### 9. Play your memories
Play your created memory like Google Photos!
- I used Custom View to do this function
<div align="center"><b>PLAY MEMORY SCREEN</b></div><br/>
<div align="center">
   <img width="301" alt="MemoryScreen" src="https://github.com/user-attachments/assets/9ab014e0-0685-469c-8640-98cb5ec3a76f">
</div>
