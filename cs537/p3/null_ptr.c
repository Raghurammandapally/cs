int main() {
  int a;
  int *p;

  p = 0x0;

  //null dereference
  a = *p;

  return 0;
}
