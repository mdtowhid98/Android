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
      // Automatically switch between light and dark
      debugShowCheckedModeBanner: false,
      home: HomeActivity(),
    );
  }
}

class HomeActivity extends StatelessWidget {
  const HomeActivity({super.key});

  MySnackBar(message, context) {
    return ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text(message))

    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Inventory App"),
        titleSpacing: 10,
        toolbarHeight: 60,
        toolbarOpacity: 1,
        elevation: 4,
        backgroundColor: Colors.green,
        actions: [

          IconButton(onPressed: () {
            MySnackBar("I am Comments", context);
          }, icon: Icon(Icons.comment)),
          IconButton(onPressed: () {
            MySnackBar("I am Search", context);
          }, icon: Icon(Icons.search)),
          IconButton(onPressed: () {
            MySnackBar("I am Settings", context);
          }, icon: Icon(Icons.settings)),
          IconButton(onPressed: () {
            MySnackBar("I am Email", context);
          }, icon: Icon(Icons.email)),

        ],
      ),

      floatingActionButton: FloatingActionButton(
        elevation: 10,
        child: Icon(Icons.add),
        backgroundColor: Colors.green,
        onPressed: () {
          MySnackBar("I am floating action button", context);
        },

      ),

      bottomNavigationBar: BottomNavigationBar(
        currentIndex: 0,
        items: [
          BottomNavigationBarItem(icon: Icon(Icons.home), label: "Home"),
          BottomNavigationBarItem(icon: Icon(Icons.message), label: "Contact"),
          BottomNavigationBarItem(icon: Icon(Icons.person), label: "Profile"),
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
            DrawerHeader(child: Text("Towhid")),
            ListTile(leading: Icon(Icons.home),
              title: Text("Home"),
              onTap: (){MySnackBar("I am Home", context);},),
            ListTile(leading: Icon(Icons.contact_mail),
                title: Text("Contact"),
              onTap: (){MySnackBar("I am Contact", context);},),
            ListTile(leading: Icon(Icons.person),
                title: Text("Profile"),
              onTap: (){MySnackBar("I am Profile", context);},),
            ListTile(leading: Icon(Icons.email),
                title: Text("Email"),
              onTap: (){MySnackBar("I am Email", context);},),
            ListTile(leading: Icon(Icons.phone),
                title: Text("Phone"),
              onTap: (){MySnackBar("I am Phone", context);},),
          ],
        ),
      ),

    );
  }
}
