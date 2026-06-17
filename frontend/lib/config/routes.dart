import 'package:go_router/go_router.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../screens/auth/splash_screen.dart';
import '../screens/auth/login_screen.dart';
import '../screens/auth/signup_screen.dart';
import '../screens/home/home_screen.dart';
import '../screens/community/community_screen.dart';
import '../screens/events/events_screen.dart';
import '../screens/fantasy/fantasy_screen.dart';
import '../screens/profile/profile_screen.dart';
import '../services/auth_service.dart';

final goRouterProvider = Provider<GoRouter>((ref) {
  final authService = ref.watch(authServiceProvider);

  return GoRouter(
    initialLocation: '/splash',
    routes: [
      GoRoute(
        path: '/splash',
        name: 'splash',
        builder: (context, state) => const SplashScreen(),
      ),
      GoRoute(
        path: '/login',
        name: 'login',
        builder: (context, state) => const LoginScreen(),
      ),
      GoRoute(
        path: '/signup',
        name: 'signup',
        builder: (context, state) => const SignupScreen(),
      ),
      GoRoute(
        path: '/home',
        name: 'home',
        builder: (context, state) => const HomeScreen(),
      ),
      GoRoute(
        path: '/community',
        name: 'community',
        builder: (context, state) => const CommunityScreen(),
      ),
      GoRoute(
        path: '/events',
        name: 'events',
        builder: (context, state) => const EventsScreen(),
      ),
      GoRoute(
        path: '/fantasy',
        name: 'fantasy',
        builder: (context, state) => const FantasyScreen(),
      ),
      GoRoute(
        path: '/profile/:userId',
        name: 'profile',
        builder: (context, state) => ProfileScreen(
          userId: state.pathParameters['userId']!,
        ),
      ),
    ],
    redirect: (context, state) async {
      final isLoggedIn = authService.isAuthenticated;
      final isSplash = state.matchedLocation == '/splash';
      final isLoginOrSignup = state.matchedLocation == '/login' ||
          state.matchedLocation == '/signup';

      if (isSplash) return null;

      if (!isLoggedIn && !isLoginOrSignup) {
        return '/login';
      }

      if (isLoggedIn && isLoginOrSignup) {
        return '/home';
      }

      return null;
    },
  );
});
