import 'package:flutter_riverpod/flutter_riverpod.dart';

final authServiceProvider = Provider<AuthService>((ref) {
  return AuthService();
});

class AuthService {
  bool get isAuthenticated => false;

  Future<void> login(String email, String password) async {
    // Implement login logic
  }

  Future<void> signup(String email, String username, String password) async {
    // Implement signup logic
  }

  Future<void> logout() async {
    // Implement logout logic
  }
}
