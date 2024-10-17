import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      theme: ThemeData(
        primarySwatch: Colors.green, // Light theme
      ),
      darkTheme: ThemeData(
        primarySwatch: Colors.amber, // Dark theme
      ),
      themeMode: ThemeMode.system,
      debugShowCheckedModeBanner: false,
      home: HomeActivity(),
    );
  }
}

class HomeActivity extends StatelessWidget {
  HomeActivity({super.key});

  void MySnackBar(String message, BuildContext context) {
    ScaffoldMessenger.of(context)
        .showSnackBar(SnackBar(content: Text(message)));
  }

  @override
  Widget build(BuildContext context) {
    // Define common button styles
    ButtonStyle elevatedButtonStyle = ElevatedButton.styleFrom(
      padding: EdgeInsets.all(10),
      backgroundColor: Colors.amber,
      foregroundColor: Colors.white,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(40)),
      ),
    );

    ButtonStyle textButtonStyle = TextButton.styleFrom(
      padding: EdgeInsets.all(10),
      backgroundColor: Colors.green, // Lighter background for TextButton
      foregroundColor: Colors.white,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(40)),
      ),
    );

    ButtonStyle outlinedButtonStyle = OutlinedButton.styleFrom(
      padding: EdgeInsets.all(10),
      side: BorderSide(color: Colors.amber, width: 2),
      backgroundColor: Colors.blueAccent, // Lighter background for TextButton
      foregroundColor: Colors.white,
      shape: RoundedRectangleBorder(
        borderRadius: BorderRadius.all(Radius.circular(40)),
      ),

    );

    return Scaffold(
      appBar: AppBar(
        title: const Text("Inventory App"),
        titleSpacing: 10,
        toolbarHeight: 60,
        toolbarOpacity: 1,
        elevation: 4,
        backgroundColor: Colors.green,
        actions: [
          IconButton(
              onPressed: () {
                MySnackBar("I am Comments", context);
              },
              icon: const Icon(Icons.comment)),
          IconButton(
              onPressed: () {
                MySnackBar("I am Search", context);
              },
              icon: const Icon(Icons.search)),
          IconButton(
              onPressed: () {
                MySnackBar("I am Settings", context);
              },
              icon: const Icon(Icons.settings)),
          IconButton(
              onPressed: () {
                MySnackBar("I am Email", context);
              },
              icon: const Icon(Icons.email)),
        ],
      ),
      floatingActionButton: FloatingActionButton(
        elevation: 10,
        child: const Icon(Icons.add),
        backgroundColor: Colors.green,
        onPressed: () {
          MySnackBar("I am floating action button", context);
        },
      ),
      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        items: [
          BottomNavigationBarItem(icon: const Icon(Icons.home), label: "Home"),
          BottomNavigationBarItem(icon: const Icon(Icons.message), label: "Contact"),
          BottomNavigationBarItem(icon: const Icon(Icons.person), label: "Profile"),
        ],
        onTap: (int index) {
          if (index == 0) {
            MySnackBar("I am Home bottom menu", context);
          }
          if (index == 1) {
            MySnackBar("I am Contact bottom menu", context);
          }
          if (index == 2) {
            MySnackBar("I am Profile bottom menu", context);
          }
        },
      ),
      drawer: Drawer(
        child: ListView(
          children: [
            DrawerHeader(
              padding: EdgeInsets.all(0),
              child: UserAccountsDrawerHeader(
                decoration: BoxDecoration(color: Colors.lightGreenAccent),
                accountName: const Text("Md Towhid Alam", style: TextStyle(color: Colors.black)),
                accountEmail: const Text("towhid@gmail.com", style: TextStyle(color: Colors.black)),
                currentAccountPicture: Image.network("https://i.postimg.cc/258wCH8j/man.png"),
                onDetailsPressed: () {
                  MySnackBar("This is my profile", context);
                },
              ),
            ),
            ListTile(
              leading: const Icon(Icons.home),
              title: const Text("Home"),
              onTap: () {
                MySnackBar("I am Home", context);
              },
            ),
            ListTile(
              leading: const Icon(Icons.contact_mail),
              title: const Text("Contact"),
              onTap: () {
                MySnackBar("I am Contact", context);
              },
            ),
            ListTile(
              leading: const Icon(Icons.person),
              title: const Text("Profile"),
              onTap: () {
                MySnackBar("I am Profile", context);
              },
            ),
            ListTile(
              leading: const Icon(Icons.email),
              title: const Text("Email"),
              onTap: () {
                MySnackBar("I am Email", context);
              },
            ),
            ListTile(
              leading: const Icon(Icons.phone),
              title: const Text("Phone"),
              onTap: () {
                MySnackBar("I am Phone", context);
              },
            ),
          ],
        ),
      ),
      body: Row(
        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
        children: [
          TextButton(
            onPressed: () {
              MySnackBar("I am text button", context);
            },
            child: const Text("Text Button"),
            style: textButtonStyle, // Apply TextButton style
          ),
          ElevatedButton(
            onPressed: () {
              MySnackBar("I am elevated button", context);
            },
            child: const Text("Elevated Button"),
            style: elevatedButtonStyle, // Apply ElevatedButton style
          ),
          OutlinedButton(
            onPressed: () {
              MySnackBar("I am outline button", context);
            },
            child: const Text("Outline Button"),
            style: outlinedButtonStyle, // Apply OutlinedButton style
          ),
        ],
      ),
    );
  }
}
