import 'package:flutter/material.dart';
import 'package:recipe_app/recipe_details/view/recipe_details_page.dart';
import 'package:recipe_app/recipe_list/view/recipe_list_page.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      initialRoute: '/details',
      routes: {
        '/list': (context) => const RecipeListPage(),
        '/details': (context) => const RecipeDetailsPage(),
      },
    );
  }
}
