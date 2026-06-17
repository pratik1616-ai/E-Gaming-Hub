import 'package:flutter/material.dart';

class BottomNavBar extends StatelessWidget {
  final int currentIndex;

  const BottomNavBar({Key? key, required this.currentIndex}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return BottomNavigationBar(
      currentIndex: currentIndex,
      items: const [
        BottomNavigationBarItem(
          icon: Icon(Icons.home),
          label: 'Home',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.people),
          label: 'Community',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.sports_esports),
          label: 'Events',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.star),
          label: 'Fantasy',
        ),
        BottomNavigationBarItem(
          icon: Icon(Icons.person),
          label: 'Profile',
        ),
      ],
      onTap: (index) {
        final routes = ['home', 'community', 'events', 'fantasy', 'profile'];
        if (index == 4) {
          Navigator.of(context).pushNamed('${routes[index]}/1');
        } else {
          Navigator.of(context).pushNamed(routes[index]);
        }
      },
    );
  }
}
