# StackMobile
Mobile App with various features of StackOverflow

- It has main activity where user can click on login button and will be directed to StackOverflow in browser and can login there and will
ask for authorization to app to access stackoverflow.
- User on successful login or already loggedin is redirected to next activity.
- On next activity data is fetched from stackoverflow and trending tags are shown.
- User need to select 4 tags compulsarily and click submit and then only user can move to next activity.
- Next activity has navigation drawer which has four items, the tags selected in previous activity, user can see all the questions of that
tag, bydefault the questions for first tag are shown.
- When user clicks on any question, question opens in webview with detailed information.
- Each question has two options, of sharing the link of question with any other app and secondly download the question and save locally.
- Question is saved locally in room database.
- All questions are displayed in recycler view with pagination, which allows only fixed number of questions to get loaded and load more
questions based on scrolling list.
- Navigation drawer has one more feature of seeing locally saved questions, which when selected shows locally saved questions, on that also
clicking will result in opening of webview, and detailed information of question in it.
- Internet availability is checked and user is prompted for that.
- User is also not allowed to save the same questions locally again and again, and one questions can be saved only once max.

Used StackExchange API and Room database for major features of this app.
